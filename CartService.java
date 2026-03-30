
package ecommerce.service;

import ecommerce.model.*;
import java.util.*;

public class CartService {
    private final Map<String, List<CartItem>> carts = new HashMap<>();
    private final ProductService productService;

    public CartService(ProductService productService) {
        this.productService = productService;
    }

    public void addToCart(String user, String pid, int qty) {
        productService.reserveStock(pid, qty);
        carts.computeIfAbsent(user, k -> new ArrayList<>())
                .add(new CartItem(pid, qty));
    }

    public void removeFromCart(String user) {
        List<CartItem> items = carts.get(user);
        if (items == null) return;
        for (CartItem i : items)
            productService.releaseStock(i.productId, i.quantity);
        carts.remove(user);
    }

    public List<CartItem> getCart(String user) {
        return carts.getOrDefault(user, new ArrayList<>());
    }

    public void viewCart(String user) {
        getCart(user).forEach(i ->
                System.out.println(i.productId + " x " + i.quantity));
    }

    public void clearCart(String user) {
        carts.remove(user);
    }
}
