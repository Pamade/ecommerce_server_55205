package ecommerce_server.ecommerce_server.stripe;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping("/api/stripe")
@Tag(name = "Payment Processing", description = "Stripe payment integration and webhook handling")
@Controller
@AllArgsConstructor
public class StripeController {
    private final StripeService stripeService;
    @PostMapping("/create-checkout-session")
    @Operation(
            summary = "Create Stripe checkout session",
            description = "Creates a new Stripe checkout session for payment processing"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Checkout session created successfully"),
            @ApiResponse(responseCode = "400", description = "Failed to create checkout session"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Map<String, String>> createSession(){
        try{
            return stripeService.createSession();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
