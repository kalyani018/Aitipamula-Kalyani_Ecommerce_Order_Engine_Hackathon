
package ecommerce.model;

public class Order {
    public String id;
    public String user;
    public double total;
    public OrderStatus status;

    public Order(String id, String user, double total, OrderStatus status) {
        this.id = id;
        this.user = user;
        this.total = total;
        this.status = status;
    }
}
