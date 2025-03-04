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
    private final PaymentStrategyFactory paymentStrategyFactory;


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
