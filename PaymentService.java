
package ecommerce.service;

import java.util.Random;

public class PaymentService {
    private final Random random = new Random();

    public boolean process(double amount) {
        return random.nextBoolean();
    }
}
