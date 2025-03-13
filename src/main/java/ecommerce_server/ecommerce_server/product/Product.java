package ecommerce_server.ecommerce_server.product;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
    @ElementCollection
    @CollectionTable(name="product_reviews", joinColumns =  @JoinColumn(name="product_id"))
    @Column(name="review")
    private List<String> reviews;
    @ElementCollection
    @CollectionTable(name="product_ratings", joinColumns = @JoinColumn(name="product_id"))
    @Column(name="rating")
    private List<@Min(1) @Max(5) Integer> ratings;
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

}
