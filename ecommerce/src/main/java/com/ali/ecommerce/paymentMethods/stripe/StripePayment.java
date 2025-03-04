package com.ali.ecommerce.paymentMethods.stripe;

import com.ali.ecommerce.DTO.CreateCheckoutSessionRequestDTO;
import com.ali.ecommerce.paymentMethods.PaymentStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.ali.ecommerce.exception.StripePaymentException;
import com.ali.ecommerce.model.CartItem;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.ali.ecommerce.exception.CartException;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Component
// the above annotation is used to register this class as a bean in the Spring context
public class StripePayment implements PaymentStrategy {

    @Value("${stripe.api.key}")
    private String stripeSecretKey;


    @Override
    public Map<String, String> pay(CreateCheckoutSessionRequestDTO request) {
        return createCheckoutSession(request);
    }

    private Map<String, String> createCheckoutSession(CreateCheckoutSessionRequestDTO request) {
        Stripe.apiKey = stripeSecretKey;

        List<CartItem> cartItems = request.getCartItems();
        String currency = request.getCurrency();
        Long orderId = request.getOrderId();
        String successUrl = request.getSuccessUrl();
        String cancelUrl = request.getCancelUrl();

        if (cartItems.isEmpty()) {
            throw new CartException("Cart is empty");
        }

        List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            lineItems.add(
                    SessionCreateParams.LineItem.builder()
                            .setQuantity(Long.valueOf(cartItem.getQuantity()))
                            .setPriceData(
                                    SessionCreateParams.LineItem.PriceData.builder()
                                            .setCurrency(currency)
                                            .setUnitAmount(cartItem.getProduct().getPrice().longValue() * 100) // Price in cents (e.g., $10.00 = 1000)
                                            .setProductData(
                                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                            .setName(cartItem.getProduct().getName())
                                                            .build()
                                            )
                                            .build()
                            )
                            .build()
            );
        }

        Map<String, String> metadata = new HashMap<>();
        metadata.put("orderId", orderId.toString());

        try {
            // Create the Checkout Session:
            SessionCreateParams params = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT) // For one-time payments
                    .setSuccessUrl(successUrl) // Redirect on success
                    .setCancelUrl(cancelUrl) // Redirect on cancel
                    .addAllLineItem(lineItems)
                    .putAllMetadata(metadata)
                    .build();

            Session session = Session.create(params);

            return Map.of("sessionId", session.getId());
        } catch (StripeException /* or Exception */ e) {
            throw new StripePaymentException(e.getMessage());
        }
    }

}
