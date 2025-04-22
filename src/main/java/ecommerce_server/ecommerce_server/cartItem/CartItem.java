package ecommerce_server.ecommerce_server.cartItem;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ecommerce_server.ecommerce_server.cart.Cart;
import ecommerce_server.ecommerce_server.product.Product;
import ecommerce_server.ecommerce_server.product.Size;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "cart")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cart_id",  nullable = false)
    @JsonBackReference // Prevents infinite recursion
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="product_id", nullable = false)
    private Product product;

    private int quantity;
    @Enumerated(EnumType.STRING)
    private Size size;
}
