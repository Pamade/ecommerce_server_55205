package ecommerce_server.ecommerce_server.ProductReview;

import ecommerce_server.ecommerce_server.product.Product;
import ecommerce_server.ecommerce_server.user.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductReviewService {

    private ProductReviewRepository reviewRepository;

    public ProductReview createReview(Product product, User user, String reviewText, int rating) {
        ProductReview review = new ProductReview();
        review.setProduct(product);
        review.setUser(user);
        review.setReview(reviewText);
        review.setRating(rating);

        return reviewRepository.save(review);
    }
}
