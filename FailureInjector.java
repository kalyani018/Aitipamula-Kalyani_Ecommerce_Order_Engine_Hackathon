
package ecommerce.util;

import java.util.Random;

public class FailureInjector {
    public static boolean enabled = false;
    private static final Random random = new Random();

    public static void randomFailure(String stage) {
        if (enabled && random.nextBoolean())
            throw new RuntimeException("Injected failure at " + stage);
    }
}
