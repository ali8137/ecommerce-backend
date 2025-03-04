package com.ali.ecommerce.service;


import com.ali.ecommerce.DTO.CreateCheckoutSessionRequestDTO;
import com.ali.ecommerce.exception.OrderException;
import com.ali.ecommerce.model.Payment;
import com.ali.ecommerce.model.PaymentMethod;
import com.ali.ecommerce.paymentMethods.PaymentStrategy;
import com.ali.ecommerce.paymentMethods.PaymentStrategyFactory;
import com.ali.ecommerce.repository.OrderRepository;
import com.ali.ecommerce.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    /* TODO: injecting the associated services of the below repositories would have been better for
        modularity, scalability and testing. add the service classes rather than the repositories below*/
    private final OrderRepository orderRepository;
//    removed in the new design pattern for making payments
//    @Value("${stripe.api.key}")
//    private String stripeSecretKey;
    private final PaymentStrategyFactory paymentStrategyFactory;

//    removed in the new design pattern for making payments
//    public Map<String, String> createCheckoutSession(CreateCheckoutSessionRequestDTO request) {
//        Stripe.apiKey = stripeSecretKey;
//
//        List<CartItem> cartItems = request.getCartItems();
//        String currency = request.getCurrency();
//        Long orderId = request.getOrderId();
//        String successUrl = request.getSuccessUrl();
//        String cancelUrl = request.getCancelUrl();
//
//        if (cartItems.isEmpty()) {
//            throw new CartException("Cart is empty");
//        }
//
//        List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();
//        for (CartItem cartItem : cartItems) {
//            lineItems.add(
//                    SessionCreateParams.LineItem.builder()
//                            .setQuantity(Long.valueOf(cartItem.getQuantity()))
//                            .setPriceData(
//                                    SessionCreateParams.LineItem.PriceData.builder()
//                                            .setCurrency(currency)
//                                            .setUnitAmount(cartItem.getProduct().getPrice().longValue() * 100) // Price in cents (e.g., $10.00 = 1000)
//                                            .setProductData(
//                                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
//                                                            .setName(cartItem.getProduct().getName())
//                                                            .build()
//                                            )
//                                            .build()
//                            )
//                            .build()
//            );
//        }
//
//        Map<String, String> metadata = new HashMap<>();
//        metadata.put("orderId", orderId.toString());
//
//        try {
//            // Create the Checkout Session:
//            SessionCreateParams params = SessionCreateParams.builder()
//                    .setMode(SessionCreateParams.Mode.PAYMENT) // For one-time payments
//                    .setSuccessUrl(successUrl) // Redirect on success
//                    .setCancelUrl(cancelUrl) // Redirect on cancel
//                    .addAllLineItem(lineItems)
//                    .putAllMetadata(metadata)
//                    .build();
//
//            Session session = Session.create(params);
//
//            return Map.of("sessionId", session.getId());
//        } catch (StripeException /* or Exception */ e) {
//            throw new StripePaymentException(e.getMessage());
//        }
//    }

    public Map<String, String> makePayment(
            CreateCheckoutSessionRequestDTO request,
            String paymentMethod
    ) {
        PaymentStrategy strategy = paymentStrategyFactory.getPaymentStrategy(paymentMethod);
        return strategy.pay(request);
    }


    public void persistPayment(String id, BigDecimal amount, Long orderId) {
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

    //    helper private methods:
}
