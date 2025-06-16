package ecommerce_server.ecommerce_server.product;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product getProductByName(String name) {
        return productRepository.findByName(name).orElseThrow(() ->  new RuntimeException("Product not found with id: " + name));
    }

    public List<Product> getAllProducts () {

        return productRepository.findAll();
    }

    @Transactional
    public void removeAsPurchaseProduct(String name, Integer quantity, Size size){
        Product product = getProductByName(name);
        Map<Size, Integer> sizeQuantities= product.getSizeQuantities();
        Integer currentQuantity = sizeQuantities.getOrDefault(size, 0);
        if (currentQuantity < quantity) {
            throw new IllegalArgumentException("Not enough quantity for size " + size);
        }
        sizeQuantities.put(size, currentQuantity - quantity);

        productRepository.save(product);
    }

//    @Transactional
//    public void addProduct(){
//        try {
//            Product product = new Product();
//            product.setName("XXX");
//            product.setPrice(19.39);
//            product.setDescription("Soft cotton t-shirt.");
//            product.setColor("Black");
//            product.setDetails("Some details");
//            product.setDressStyle(DressStyle.CASUAL);
//            product.setDressType(DressType.T_SHIRT);
//
//            // Set sizes and quantities
//            Map<Size, Integer> sizeQuantities = new HashMap<>();
//            sizeQuantities.put(Size.S, 10);
//            sizeQuantities.put(Size.M, 15);
//            sizeQuantities.put(Size.L, 5);
//            product.setSizeQuantities(sizeQuantities);
//
//            // Set image URLs
//            List<String> images = List.of(
//                    "https://example.com/images/tshirt  -front.jpg",
//                    "https://example.com/images/tshirt-back.jpg"
//            );
//            product.setImages(images);
//
//            // Save product
//            productRepository.save(product);
//            productRepository.flush();
//        } catch (Exception e) {
//            System.out.println(e);
//            throw e;  // Rethrow to allow transaction rollback
//        }
//
//    }

}
