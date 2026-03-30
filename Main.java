
package ecommerce;

import ecommerce.service.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ProductService productService = new ProductService();
        CartService cartService = new CartService(productService);
        CouponService couponService = new CouponService();
        PaymentService paymentService = new PaymentService();
        LogService logService = new LogService();
        FraudService fraudService = new FraudService();
        EventService eventService = new EventService(logService);

        OrderService orderService = new OrderService(
                productService, cartService,
                paymentService, couponService,
                eventService, fraudService, logService
        );

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== E-Commerce Engine ===");
            System.out.println("1. Add Product");
            System.out.println("2. View Products");
            System.out.println("3. Add to Cart");
            System.out.println("4. Remove from Cart");
            System.out.println("5. View Cart");
            System.out.println("6. Apply Coupon");
            System.out.println("7. Place Order");
            System.out.println("8. Cancel Order");
            System.out.println("9. View Orders");
            System.out.println("10. Low Stock Alert");
            System.out.println("11. Return Product");
            System.out.println("12. Simulate Concurrent Users");
            System.out.println("13. View Logs");
            System.out.println("14. Trigger Failure Mode");
            System.out.println("0. Exit");

            int choice = sc.nextInt();

            try {
                switch (choice) {
                    case 1 -> {
                        System.out.print("ID: ");
                        String id = sc.next();
                        System.out.print("Name: ");
                        String name = sc.next();
                        System.out.print("Stock: ");
                        int stock = sc.nextInt();
                        System.out.print("Price: ");
                        double price = sc.nextDouble();
                        productService.addProduct(id, name, stock, price);
                    }
                    case 2 -> productService.viewProducts();
                    case 3 -> {
                        System.out.print("User: ");
                        String user = sc.next();
                        System.out.print("Product: ");
                        String pid = sc.next();
                        System.out.print("Qty: ");
                        int q = sc.nextInt();
                        cartService.addToCart(user, pid, q);
                    }
                    case 4 -> {
                        System.out.print("User: ");
                        cartService.removeFromCart(sc.next());
                    }
                    case 5 -> {
                        System.out.print("User: ");
                        cartService.viewCart(sc.next());
                    }
                    case 6 -> {
                        System.out.print("User: ");
                        String user = sc.next();
                        System.out.print("Coupon: ");
                        couponService.applyCoupon(user, sc.next());
                    }
                    case 7 -> {
                        System.out.print("User: ");
                        orderService.placeOrder(sc.next());
                    }
                    case 8 -> {
                        System.out.print("Order ID: ");
                        orderService.cancelOrder(sc.next());
                    }
                    case 9 -> orderService.viewOrders();
                    case 10 -> productService.lowStockAlert();
                    case 11 -> {
                        System.out.print("Order ID: ");
                        orderService.returnProduct(sc.next());
                    }
                    case 12 -> orderService.simulateConcurrency();
                    case 13 -> logService.showLogs();
                    case 14 -> orderService.enableFailureMode();
                    case 0 -> System.exit(0);
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
