
package ecommerce.service;

import ecommerce.model.Product;
import java.util.*;

public class ProductService {
    private final Map<String, Product> products = new HashMap<>();

    public synchronized void addProduct(String id, String name, int stock, double price) {
        if (products.containsKey(id))
            throw new RuntimeException("Duplicate product ID");
        if (stock < 0)
            throw new RuntimeException("Negative stock");
        products.put(id, new Product(id, name, stock, price));
    }

    public synchronized void reserveStock(String id, int qty) {
        Product p = products.get(id);
        if (p.stock < qty)
            throw new RuntimeException("Out of stock");
        p.stock -= qty;
    }

    public synchronized void releaseStock(String id, int qty) {
        products.get(id).stock += qty;
    }

    public void viewProducts() {
        products.values().forEach(p ->
                System.out.println(p.id + " " + p.name + " " + p.stock));
    }

    public void lowStockAlert() {
        products.values().stream()
                .filter(p -> p.stock < 3)
                .forEach(p -> System.out.println("LOW: " + p.name));
    }

    public Product get(String id) {
        return products.get(id);
    }
}
