package ecommerce_server.ecommerce_server.cart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import ecommerce_server.ecommerce_server.cartItem.CartItem;
import ecommerce_server.ecommerce_server.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "user")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name="user_id", nullable = false)
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    @JsonManagedReference // Prevents infinite recursion
    private List<CartItem> cartItems;

    private double totalPrice;

    public void addItem (CartItem cartItem) {
        this.cartItems.add(cartItem);
    }

    public void updateTotalPrice(){
        this.totalPrice = 0;
        if (!cartItems.isEmpty()) {
            this.totalPrice = cartItems.stream().mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity()).sum();
        }

    }
}
