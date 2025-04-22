package ecommerce_server.ecommerce_server.cart;

import ecommerce_server.ecommerce_server.cartItem.CartItem;
import ecommerce_server.ecommerce_server.cartItem.CartItemDto;
import ecommerce_server.ecommerce_server.product.Product;
import ecommerce_server.ecommerce_server.product.ProductRepository;
import ecommerce_server.ecommerce_server.product.Size;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/cart")
@AllArgsConstructor
public class CartController {

    private final CartService cartService;
    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestBody CartItemDto cartItemDto) {
        try {
            cartService.addToCart(cartItemDto);
            return ResponseEntity.ok("Item added");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/change-quantity/{id}/{newQuantity}")
    public ResponseEntity<String> changeQuantityOfCartItem(@PathVariable String id,@PathVariable Integer newQuantity){
        System.out.println(id);
        System.out.println(newQuantity);
        try {
            boolean canIncreaseQuantity = cartService.changeItemQuantity(id, newQuantity);
            if (canIncreaseQuantity) {
                return ResponseEntity.ok("Item value changed");
            }
            return ResponseEntity.badRequest().body("you can't add more of this type of product");
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/remove/{id}")
    public ResponseEntity<String> removeFromCart(@PathVariable String id){
        System.out.println(id);
        try {
            cartService.removeFromCart(id);
            return ResponseEntity.ok("Item Removed");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/get-all")
    public ResponseEntity<List<CartItem>> getAll() {
        List<CartItem> cartItems = cartService.getCartItemsFromUser();
        return ResponseEntity.ok(cartItems);
    }

}
