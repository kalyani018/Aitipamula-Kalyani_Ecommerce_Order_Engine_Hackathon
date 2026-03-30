
package ecommerce.service;

import ecommerce.model.Order;
import java.util.*;

public class FraudService {
    private final Map<String, List<Long>> userOrders = new HashMap<>();

    public void check(Order order) {
        long now = System.currentTimeMillis();
        userOrders.computeIfAbsent(order.user, k -> new ArrayList<>()).add(now);
    }
}
