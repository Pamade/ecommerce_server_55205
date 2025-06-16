package ecommerce_server.ecommerce_server.stripe;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import ecommerce_server.ecommerce_server.cart.Cart;
import ecommerce_server.ecommerce_server.cart.CartRepository;
import ecommerce_server.ecommerce_server.cartItem.CartItem;
import ecommerce_server.ecommerce_server.product.Product;
import ecommerce_server.ecommerce_server.product.ProductRepository;
import ecommerce_server.ecommerce_server.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StripeService {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final SecurityUtils securityUtils;
    private final String stripeSecretKey;
    @Autowired
    public StripeService(ProductRepository productRepository, CartRepository cartRepository,
                         SecurityUtils securityUtils,
                         @Value("${stripe.secret.key}") String stripeSecretKey) {
        this.productRepository = productRepository;
        this.stripeSecretKey = stripeSecretKey;
        this.cartRepository = cartRepository;
        this.securityUtils = securityUtils;
    }

    public ResponseEntity<Map<String, String>> createSession() throws StripeException {
        System.out.println(stripeSecretKey);

        final String successUrl = "http://localhost:5173/success";
        final String cancelUrl = "http://localhost:5173/cancel";
        UserDetails userDetails = securityUtils.getAuthUser().orElseThrow();

        Cart cart = cartRepository.findByUserEmail(userDetails.getUsername());

        Stripe.apiKey = stripeSecretKey;

        SessionCreateParams.Builder sessionBuilder = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(successUrl)
                .setCancelUrl(cancelUrl);

        for (CartItem item : cart.getCartItems()) {
            Product product = item.getProduct();
            long unitAmount = Math.round(product.getPrice() * 100);

            sessionBuilder.addLineItem(
                    SessionCreateParams.LineItem.builder()
                            .setQuantity((long) item.getQuantity())
                            .setPriceData(
                                    SessionCreateParams.LineItem.PriceData.builder()
                                            .setCurrency("usd")
                                            .setUnitAmount(unitAmount)
                                            .setProductData(
                                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                            .setName(product.getName() + " _ " + item.getSize())
                                                            .setDescription(product.getDescription())
                                                            .addImage(product.getImages().get(0))
                                                            .build()
                                            )
                                            .build()
                            )
                            .build()
            );
        }
        //set item for email
        sessionBuilder.setCustomerEmail(userDetails.getUsername());
        //set item for shipping
        sessionBuilder.addLineItem(
                SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(
                                SessionCreateParams.LineItem.PriceData.builder()
                                        .setCurrency("USD")
                                        .setUnitAmount(1500L) // 15.00 zł
                                        .setProductData(
                                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                        .setName("Wysyłka kurierska")
                                                        .build()
                                        )
                                        .build()
                        )
                        .build()
        );
        Session session = Session.create(sessionBuilder.build());
        Map<String, String> responseData = new HashMap<>();
        responseData.put("sessionId", session.getId());
        return ResponseEntity.ok(responseData);


    }



}
