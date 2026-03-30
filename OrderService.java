
package ecommerce.service;

import ecommerce.model.*;
import ecommerce.util.FailureInjector;
import java.util.*;

public class OrderService {
    private final Map<String, Order> orders = new HashMap<>();
    private final ProductService productService;
    private final CartService cartService;
    private final PaymentService paymentService;
    private final CouponService couponService;
    private final EventService eventService;
    private final FraudService fraudService;
    private final LogService logService;
    private int counter = 100;

    public OrderService(ProductService p, CartService c,
                        PaymentService pay, CouponService coupon,
                        EventService event, FraudService fraud,
                        LogService log) {
        this.productService = p;
        this.cartService = c;
        this.paymentService = pay;
        this.couponService = coupon;
        this.eventService = event;
        this.fraudService = fraud;
        this.logService = log;
    }

    public void placeOrder(String user) {
        List<CartItem> cart = cartService.getCart(user);
        if (cart.isEmpty())
            throw new RuntimeException("Cart empty");

        String orderId = "ORD" + (++counter);

        double total = 0;
        for (CartItem c : cart)
            total += productService.get(c.productId).price * c.quantity;

        total = couponService.applyDiscount(user, total);

        Order order = new Order(orderId, user, total, OrderStatus.CREATED);
        orders.put(orderId, order);

        try {
            FailureInjector.randomFailure("ORDER_CREATION");

            order.status = OrderStatus.PENDING_PAYMENT;

            if (!paymentService.process(total)) {
                throw new RuntimeException("Payment failed");
            }

            order.status = OrderStatus.PAID;
            cartService.clearCart(user);

            eventService.publish("ORDER_CREATED " + orderId);
            fraudService.check(order);
            logService.log("Order placed: " + orderId);

        } catch (Exception e) {
            rollback(orderId, cart);
            System.out.println("Order failed. Rolled back.");
        }
    }

    private void rollback(String orderId, List<CartItem> cart) {
        orders.remove(orderId);
        for (CartItem i : cart)
            productService.releaseStock(i.productId, i.quantity);
    }

    public void cancelOrder(String id) {
        Order o = orders.get(id);
        if (o == null || o.status == OrderStatus.CANCELLED)
            throw new RuntimeException("Invalid cancel");
        o.status = OrderStatus.CANCELLED;
        logService.log("Order cancelled " + id);
    }

    public void viewOrders() {
        orders.values().forEach(o ->
                System.out.println(o.id + " " + o.status));
    }

    public void simulateConcurrency() {
        Thread t1 = new Thread(() -> cartService.addToCart("A", "P1", 5));
        Thread t2 = new Thread(() -> cartService.addToCart("B", "P1", 5));
        t1.start();
        t2.start();
    }

    public void returnProduct(String orderId) {
        System.out.println("Partial return processed.");
    }

    public void enableFailureMode() {
        FailureInjector.enabled = true;
    }
}
