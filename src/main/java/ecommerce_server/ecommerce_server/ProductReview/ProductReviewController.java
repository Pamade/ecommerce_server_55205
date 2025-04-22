package ecommerce_server.ecommerce_server.ProductReview;

import ecommerce_server.ecommerce_server.product.Product;
import ecommerce_server.ecommerce_server.product.ProductRepository;
import ecommerce_server.ecommerce_server.user.User;
import ecommerce_server.ecommerce_server.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/reviews")
@AllArgsConstructor
public class ProductReviewController {

    private final UserRepository userRepository;
    private final ProductReviewService productReviewService;
    private final ProductRepository productRepository;
    @PostMapping("/add")
    public ProductReview addReview(@RequestBody ProductReviewDTO reviewDto){

        try {
            Product product = productRepository.findById(reviewDto.getProductId()).orElseThrow();
            User user = (User) userRepository.findByEmail(reviewDto.getEmail()).orElseThrow();
            return productReviewService.createReview(product, user, reviewDto.getReviewText(), reviewDto.getRating());
        }
        catch (NoSuchElementException e) {
            e.printStackTrace();
        }

        return null;
    }
}
