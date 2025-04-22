package ecommerce_server.ecommerce_server.ProductReview;


import lombok.Data;

@Data
public class ProductReviewDTO {
    private int productId;
    private String email;
    private String reviewText;
    private int rating;

    // Getters and Setters
}
