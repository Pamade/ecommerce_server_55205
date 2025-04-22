    package ecommerce_server.ecommerce_server.ProductReview;

    import com.fasterxml.jackson.annotation.JsonBackReference;
    import com.fasterxml.jackson.annotation.JsonIgnore;
    import ecommerce_server.ecommerce_server.product.Product;
    import ecommerce_server.ecommerce_server.user.User;
    import jakarta.persistence.*;
    import jakarta.validation.constraints.Max;
    import jakarta.validation.constraints.Min;
    import lombok.Getter;
    import lombok.Setter;

    @Entity
    @Getter
    @Setter
    @Table(name="product_reviews")
    public class ProductReview {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;

        @ManyToOne
        @JoinColumn(name="product_id", nullable = false)
        @JsonBackReference
        private Product product;

        @ManyToOne
        @JoinColumn(name="user_id", nullable = false)
        private User user;

        @Column(name = "review")
        private String review;

        @Min(1)
        @Max(5)
        @Column(name="rating")
        private Integer rating;


    }
