package ecommerce_server.ecommerce_server.product;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(
            summary = "Get product by name",
            description = "Retrieves a specific product by its name"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Product> getProduct(@PathVariable String name){
        try {
            return ResponseEntity.ok(productService.getProductByName(name));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    @Operation(
            summary = "Get all products",
            description = "Retrieves all available products in the catalog"
    )
    @ApiResponse(responseCode = "200", description = "All products retrieved successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(type = "array")))
    public ResponseEntity<List<Product>> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

//    @PostMapping("/add")
//    public void addProduct(){
//        System.out.println("Product added");
//        productService.addProduct();
//    }
}
