package ecommerce_server.ecommerce_server.cart;

import ecommerce_server.ecommerce_server.cartItem.CartItem;
import ecommerce_server.ecommerce_server.cartItem.CartItemDto;
import ecommerce_server.ecommerce_server.product.Product;
import ecommerce_server.ecommerce_server.product.ProductRepository;
import ecommerce_server.ecommerce_server.product.Size;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/cart")
@Tag(name = "Cart Management", description = "Operations related to shopping cart management")
@AllArgsConstructor
public class CartController {

    private final CartService cartService;
    @PostMapping("/add")
    @Operation(
            summary = "Add item to cart",
            description = "Adds a new item to the shopping cart with specified quantity"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item added successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{\"body\": \"Item added\"}"))),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Item not found")
    })
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
    @Operation(
            summary = "Change item quantity",
            description = "Updates the quantity of a specific item in the cart"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quantity updated successfully"),
            @ApiResponse(responseCode = "400", description = "Cannot add more of this type of product"),
            @ApiResponse(responseCode = "404", description = "Item not found")
    })
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

    @DeleteMapping("/remove/{id}")
    @Operation(
            summary = "Remove item from cart",
            description = "Removes a specific item from the shopping cart"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item removed successfully"),
            @ApiResponse(responseCode = "404", description = "Item not found")
    })
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
    @Operation(
            summary = "Get all products",
            description = "Retrieves all available products in the catalog"
    )
    @ApiResponse(responseCode = "200", description = "All products retrieved successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(type = "array")))
    public ResponseEntity<List<CartItem>> getAll() {
        List<CartItem> cartItems = cartService.getCartItemsFromUser();
        return ResponseEntity.ok(cartItems);
    }

}
