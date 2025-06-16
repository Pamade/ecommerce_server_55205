package ecommerce_server.ecommerce_server.cart;

import ecommerce_server.ecommerce_server.cartItem.CartItem;
import ecommerce_server.ecommerce_server.cartItem.CartItemDto;
import ecommerce_server.ecommerce_server.cartItem.CartItemRepository;
import ecommerce_server.ecommerce_server.product.Product;
import ecommerce_server.ecommerce_server.product.ProductRepository;
import ecommerce_server.ecommerce_server.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final SecurityUtils securityUtils;
    private final CartItemRepository cartItemRepository;

    public CartItem getItemById(String id){
        return cartItemRepository.findById(Long.valueOf(id)).orElseThrow();
    }

    @Transactional
    public CartItem addToCart(@RequestBody CartItemDto cartItemDto) {

        try {
            Cart cart = cartRepository.findByUserEmail(cartItemDto.getUserEmail());
            Product product = productRepository.findById(cartItemDto.getProductId()).orElseThrow();
            CartItem cartItem = CartItem.builder()
                    .size(cartItemDto.getSize())
                    .quantity(cartItemDto.getQuantity())
                    .product(product)
                    .cart(cart)
                    .build();
            List<CartItem> cartItems = cart.getCartItems();

            // If there are no items in the cart, simply add the new cart item
            if (cartItems.isEmpty()) {
                cart.addItem(cartItem);  // Assuming Cart has addItem method to add CartItem
            } else {
                boolean itemUpdated = false;

                // Go through existing items and update quantities if the same product exists
                for (CartItem existingItem : cartItems) {
                    boolean isSameId = existingItem.getProduct().getId() == cartItem.getProduct().getId();
                    boolean isSameSize = existingItem.getSize() == cartItem.getSize();
                    if (isSameSize && isSameId) {
                        existingItem.setQuantity(existingItem.getQuantity() + cartItem.getQuantity());
                        itemUpdated = true;
                        break;
                    }
                }

                // If the product was not found, add it as a new CartItem
                if (!itemUpdated) {
                    cartItems.add(cartItem);
                }
            }

            // Save the cart, which will update the cartItems list
            cartRepository.save(cart);

            return cartItem;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    @Transactional
    public void removeFromCart(String id){
        System.out.println(id);
        try {
            CartItem item = getItemById(id);
            Cart cart = item.getCart();
            cart.getCartItems().remove(item);
            item.setCart(null);
            cartItemRepository.delete(item);
            cart.updateTotalPrice();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void clearCart(String email){
        try {
            System.out.println(email + "SERVICE EMAILLLLLL");
            Cart cart = cartRepository.findByUserEmail(email);
            cart.getCartItems().clear();
            cart.setTotalPrice(0);
            cartRepository.save(cart);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Transactional
    public boolean changeItemQuantity(String id, Integer newQuantity) {
        //get product all quantities

        CartItem item = cartItemRepository.findById(Long.valueOf(id)).orElseThrow();
        Product product = productRepository.findById(item.getProduct().getId()).orElseThrow();
        Integer quantitiesOfProductInCart = product.getSizeQuantities().get(item.getSize());
        if (newQuantity > quantitiesOfProductInCart) {
            return false;
        } else item.setQuantity(newQuantity);

        return true;
    }

    @Transactional
    public  List<CartItem> getCartItemsFromUser() {
        Optional<UserDetails> optionalUser = securityUtils.getAuthUser();

        if (optionalUser.isEmpty()) {
            return new ArrayList<>();
        }

        UserDetails userDetails = optionalUser.get();
        Cart cart = cartRepository.findByUserEmail(userDetails.getUsername());
        return cart.getCartItems();

    }
}
