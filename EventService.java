
package ecommerce.service;

import java.util.*;

public class EventService {
    private final Queue<String> queue = new LinkedList<>();
    private final LogService logService;

    public EventService(LogService logService) {
        this.logService = logService;
    }

    public void publish(String event) {
        queue.add(event);
        logService.log("EVENT: " + event);
    }
}
