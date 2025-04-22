package ecommerce_server.ecommerce_server.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import ecommerce_server.ecommerce_server.ProductReview.ProductReview;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double price;
    private String name;
    private String description;
    private String color;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ProductReview> productReviews;
    @ElementCollection
    @CollectionTable(name="product_size_quantities", joinColumns = @JoinColumn(name="product_id"))
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name="size")
    @Column(name="quantity")
    private Map<Size, Integer> sizeQuantities;
    @ElementCollection
    @CollectionTable(name="product_images", joinColumns = @JoinColumn(name="product_id"))
    @Column(name="image_url")
    private List<String> images;
    @Enumerated(EnumType.STRING)
    private DressType dressType;
    @Enumerated(EnumType.STRING)
    private DressStyle dressStyle;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    private String details;


    @PrePersist
    @PreUpdate
    private void ensureDefaultImage() {
        if (images == null || images.isEmpty()) {
            images = new ArrayList<>();
            images.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS-RcH3_rFP8ZmSEgjhZy5pv4O4bLl-SwZGsA&s");  // Set your default image URL here
        }
    }
}
