package ecommerce_server.ecommerce_server.product;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int quantity;
    private double price;
    private String name;
    private String description;
    private String color;
    @ElementCollection
    @CollectionTable(name="product_size_quantities", joinColumns = @JoinColumn(name="product_id"))
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name="size")
    @Column(name="quantity")
    private List<Size> sizes;
    @ElementCollection
    @CollectionTable(name="product_images", joinColumns = @JoinColumn(name="product_id"))
    @Column(name="image_url")
    private List<String> images;
    private DressType dressType;
    private DressStyle dressStyle;
    private String details;

}
