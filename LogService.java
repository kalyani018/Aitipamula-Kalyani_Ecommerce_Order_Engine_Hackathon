
package ecommerce.service;

import java.util.*;

public class LogService {
    private final List<String> logs = new ArrayList<>();

    public void log(String msg) {
        logs.add(new Date() + " " + msg);
    }

    public void showLogs() {
        logs.forEach(System.out::println);
    }
}
