package ecommerce_server.ecommerce_server.cart;

import ecommerce_server.ecommerce_server.cartItem.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserEmail(String userEmail);
}
