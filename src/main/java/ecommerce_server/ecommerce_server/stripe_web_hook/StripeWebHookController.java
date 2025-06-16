package ecommerce_server.ecommerce_server.stripe_web_hook;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionRetrieveParams;
import ecommerce_server.ecommerce_server.cart.Cart;
import ecommerce_server.ecommerce_server.cart.CartRepository;
import ecommerce_server.ecommerce_server.cart.CartService;
import ecommerce_server.ecommerce_server.product.ProductService;
import ecommerce_server.ecommerce_server.product.Size;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/webhook")
@Tag(name = "Stripe webhook", description = "Webhook handling")
public class StripeWebHookController {
    @Value("${stripe.webhook}")
    private String endpointSecret;

    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;

    public StripeWebHookController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    //this wont work without setting up a webhook
    @PostMapping
    @Operation(
            summary = "Handle Stripe webhook",
            description = "Processes Stripe webhook events for payment status updates",
            hidden = true // Hide from Swagger UI as this is for Stripe internal use
    )
    public ResponseEntity<String> handleStripeWebhook(@RequestBody String payload,
                                                      @RequestHeader("Stripe-Signature") String sigHeader) throws StripeException {
        Event event;

        try {
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
        } catch (SignatureVerificationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("⚠️ Invalid signature");
        }
        System.out.println(event);

        if ("checkout.session.completed".equals(event.getType())) {
            EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
            if (dataObjectDeserializer.getObject().isPresent()) {
                StripeObject stripeObject = dataObjectDeserializer.getObject().get();

                if (stripeObject instanceof Session) {
                    Session session = (Session) stripeObject;
                    String sessionId = session.getId();
                    Session fullSession = Session.retrieve(sessionId,  SessionRetrieveParams.builder()
                                    .addExpand("line_items")
                                    .build(),
                            null);
                    List<LineItem> items = fullSession.getLineItems().getData();
                    for (LineItem item : items) {
                        System.out.println(item);
                        Long productQuantity = item.getQuantity();
                        String itemName = item.getDescription();
                        System.out.println(itemName);
                        if (itemName.contains("_")) {
                            String[] itemNameAsArr = itemName.split(" _ ");
                            String productName = itemNameAsArr[0];
                            Size productSize = Size.valueOf(itemNameAsArr[1]);
                            productService.removeAsPurchaseProduct(productName, productQuantity.intValue(),productSize);
                        }


                    }

                    String customerEmail = session.getCustomerDetails().getEmail();

                    cartService.clearCart(customerEmail);
//                    for (LineItem item : sessionItems) {
//                        Long itemQuantity = item.getQuantity();
//                        String itemName = item.getPrice().getMetadata().get("name");
//                    }


                }
            } else ResponseEntity.badRequest().build();
        }



        return ResponseEntity.ok("✅ Event received");
    }
}
