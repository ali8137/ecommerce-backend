package com.ali.ecommerce.service;


import com.ali.ecommerce.DTO.CreateCheckoutSessionRequestDTO;
import com.ali.ecommerce.exception.CartException;
import com.ali.ecommerce.exception.OrderException;
import com.ali.ecommerce.exception.StripePaymentException;
import com.ali.ecommerce.model.CartItem;
import com.ali.ecommerce.model.Payment;
import com.ali.ecommerce.model.PaymentMethod;
import com.ali.ecommerce.repository.OrderRepository;
import com.ali.ecommerce.repository.PaymentRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    /* TODO: injecting the associated services of the below repositories would have been better for
        modularity, scalability and testing. add the service classes rather than the repositories below*/
    private final OrderRepository orderRepository;
    @Value("${stripe.api.key}")
    private String stripeSecretKey;

    @PostMapping("/create-checkout-session")
    public Map<String, String> createCheckoutSession(@Valid @RequestBody CreateCheckoutSessionRequestDTO request) {
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


    //    helper private methods:
    private void persistPayment(String id, BigDecimal amount, Long orderId) {
        Payment payment = Payment.builder()
                .paymentMethod(PaymentMethod.STRIPE)
                .paymentDate(LocalDateTime.now())
                .amount(amount)
                .transactionId(id)
                .build();

        var order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException("Order not found"));

        payment.setOrder(order);

        paymentRepository.save(payment);
    }
}
