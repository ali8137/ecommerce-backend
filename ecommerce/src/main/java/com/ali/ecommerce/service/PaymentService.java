package com.ali.ecommerce.service;


import com.ali.ecommerce.DTO.CreateCheckoutSessionRequestDTO;
import com.ali.ecommerce.exception.CartException;
import com.ali.ecommerce.exception.OrderException;
import com.ali.ecommerce.exception.StripePaymentException;
import com.ali.ecommerce.model.CartItem;
import com.ali.ecommerce.model.Payment;
import com.ali.ecommerce.model.PaymentMethod;
import com.ali.ecommerce.model.Product;
import com.ali.ecommerce.repository.OrderRepository;
import com.ali.ecommerce.repository.PaymentRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
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

    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);
    private final PaymentRepository paymentRepository;
//  - injecting the associated services of the below repositories would have
//    been better for modularity, scalability and testing:
    /* TODO: add the service classes rather than the repositories below*/
    private final OrderRepository orderRepository;
    @Value("${stripe.api.key}")
    private String stripeSecretKey;
//
//    @Autowired
//    public PaymentService(PaymentRepository paymentRepository, OrderRepository orderRepository) {
//        this.paymentRepository = paymentRepository;
//        this.orderRepository = orderRepository;
//    }

//    public ClassName1 method1(ParameterClass1 obj1) {
//
//        //    business logic
//        //    database operations
//        //    file operations
//        //    network operations
//        //    data validation
//        //    data transformation
//        //    DTO-to-class conversion
//        //    class-to-DTO conversion
//        //    event-driven handling
//        //    email notification sending
//        //    caching
//        //    security-related operations (like JWT token generation, password encryption, etc.)
//        //    AI integration
//        //    exception handling
//        //    logging
//
//    }


//    public Map<String, String> createCheckoutSession(Map<String, Object> requestBody) {
//
//        // product details from the frontend:
////        Long amount = (Long) requestBody.get("amount");
//        long amount = Long.parseLong(requestBody.get("amount").toString());
////      - changes the above from: "Long amount = Long.valueOf(requestBody.get("amount").toString());"
////        to : "long amount = Long.parseLong(requestBody.get("amount").toString());". because using Long
////        was not necessary, and using long is more efficient. thus, using long is more
////        appropriate for this case.
//        String currency = (String) requestBody.get("currency");
//        Long orderId = Long.valueOf(requestBody.get("orderId").toString());
////        Long orderId = (Long) requestBody.get("orderId");
//
//        try {
//            // Create a Checkout Session
//            SessionCreateParams params = SessionCreateParams.builder()
//                    .addLineItem(SessionCreateParams.LineItem.builder()
//                            .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
//                                    .setCurrency(currency)
//                                    .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
//                                            .setName("Product Name")
//                                            .build())
//                                    .setUnitAmount(amount)
//                                    .build())
//                            .setQuantity(1L)
//                            .build())
//                    .setMode(SessionCreateParams.Mode.PAYMENT)
//                    .setSuccessUrl("http://localhost:5173/payment/success")
//                    .setCancelUrl("http://localhost:5173/payment/cancel")
//                    .build();
//
//            Session session = Session.create(params);
//            log.info("session: {}", session);
//
//            // save payment details to the database:
//            this.persistPayment(session.getId(), BigDecimal.valueOf(amount), orderId);
//
//
//            // return the session id to the frontend
////            Map<String, String> response = new HashMap<>();
////            response.put("id", session.getId());
////            return response;
//            // or:
//            return Map.of("id", session.getId());
//        } catch (StripeException /* or Exception */ e) {
//            throw new RuntimeException("Stripe session creation failed" + e.getMessage());
//        }
//    }





    @PostMapping("/create-checkout-session")
    public Map<String, String> createCheckoutSession(@Valid @RequestBody CreateCheckoutSessionRequestDTO request) {
        Stripe.apiKey = stripeSecretKey;

        List<CartItem> cartItems = request.getCartItems();
        String currency = request.getCurrency();
        Long orderId = request.getOrderId();
        String successUrl = request.getSuccessUrl();
        String cancelUrl = request.getCancelUrl();

        log.info("cartItems: {}", cartItems);
        log.info("currency: {}", currency);
        log.info("orderId: {}", orderId);
        log.info("successUrl: {}", successUrl);
        log.info("cancelUrl: {}", cancelUrl);

        if (cartItems.isEmpty()) {
//            return ResponseEntity.badRequest().body("Cart is empty");
            throw new CartException("Cart is empty");
        }

        List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            lineItems.add(
                    SessionCreateParams.LineItem.builder()
                            .setQuantity(Long.valueOf(cartItem.getQuantity()))
                            .setPriceData(
                                    SessionCreateParams.LineItem.PriceData.builder()
                                            .setCurrency(currency) // Replace with your desired currency
//                                            .setUnitAmount(cartItem.getPrice().longValue()) // Price in cents (e.g., $10.00 = 1000)
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
        log.info("metadata: {}", metadata);

        try {
            // Create the Checkout Session
            SessionCreateParams params = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT) // For one-time payments
                    .setSuccessUrl(successUrl) // Redirect on success
                    .setCancelUrl(cancelUrl) // Redirect on cancel
                    .addAllLineItem(lineItems)
                    .putAllMetadata(metadata)
                    .build();

            Session session = Session.create(params);
            log.info("session: {}", session);

            return Map.of("sessionId", session.getId());
        } catch (StripeException /* or Exception */ e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//            or:
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
