
package ecommerce.service;

import java.util.*;

public class CouponService {
    private final Map<String, String> userCoupons = new HashMap<>();

    public void applyCoupon(String user, String coupon) {
        userCoupons.put(user, coupon);
    }

    public double applyDiscount(String user, double total) {
        if (total > 1000) total *= 0.9;
        if (userCoupons.containsKey(user)) {
            String c = userCoupons.get(user);
            if (c.equals("SAVE10")) total *= 0.9;
            if (c.equals("FLAT200")) total -= 200;
        }
        return total;
    }
}
