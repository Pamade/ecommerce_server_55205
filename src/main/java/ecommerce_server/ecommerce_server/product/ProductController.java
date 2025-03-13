package ecommerce_server.ecommerce_server.product;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/")
    public ResponseEntity<List<Product>> showProductsOfType() {

        return null;
    }

    @GetMapping("/{name}")
    public ResponseEntity<Product> getProduct(@PathVariable String name){
        try {
            return ResponseEntity.ok(productService.getProductByName(name));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

//    @PostMapping("/add")
//    public void addProduct(){
//        System.out.println("Product added");
//        productService.addProduct();
//    }
}
