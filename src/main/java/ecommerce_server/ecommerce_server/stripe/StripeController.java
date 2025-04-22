package ecommerce_server.ecommerce_server.stripe;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping("/api/stripe")
@Controller
@AllArgsConstructor
public class StripeController {
    private final StripeService stripeService;
    @PostMapping("/create-checkout-session")
    public ResponseEntity<Map<String, String>> createSession(){
        try{
            return stripeService.createSession();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
