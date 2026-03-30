
package ecommerce.model;

public class CartItem {
    public String productId;
    public int quantity;

    public CartItem(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}
