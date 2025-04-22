package ecommerce_server.ecommerce_server.cartItem;

import ecommerce_server.ecommerce_server.cart.Cart;
import ecommerce_server.ecommerce_server.product.Product;
import ecommerce_server.ecommerce_server.product.Size;
import ecommerce_server.ecommerce_server.user.User;
import lombok.Data;

@Data
public class CartItemDto {
    private int productId;
    private Size size;
    private int quantity;
    private String userEmail;
}
