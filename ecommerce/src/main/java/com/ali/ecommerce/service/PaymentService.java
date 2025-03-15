package com.ali.ecommerce.service;


import com.ali.ecommerce.DTO.CreateCheckoutSessionRequestDTO;
import com.ali.ecommerce.exception.OrderException;
import com.ali.ecommerce.model.OrderStatus;
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


//    public void persistPayment(String id, BigDecimal amount, Long orderId) {
//        // create the payment:
//        Payment payment = Payment.builder()
//                .paymentMethod(PaymentMethod.STRIPE)
//                .paymentDate(LocalDateTime.now())
//                .amount(amount)
//                .transactionId(id)
//                .build();
//
//        // get the order from the database:
//        var order = orderRepository.findById(orderId)
//                .orElseThrow(() -> new OrderException("Order not found"));
//
//        // set the order status to "completed":
//        order.setOrderStatus(OrderStatus.COMPLETED);
//
//        // associate the payment with the order (foreign key):
//        payment.setOrder(order);
//
//        // save the payment to the database:
//        paymentRepository.save(payment);
//    }

//     version 2:
    public void persistPayment(String id, Long orderId) {
        // create the payment:
        Payment payment = Payment.builder()
                .paymentMethod(PaymentMethod.STRIPE)
                .paymentDate(LocalDateTime.now())
                .transactionId(id)
                .build();

        // get the order from the database:
        var order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException("Order not found"));

        // set the order status to "completed":
        order.setOrderStatus(OrderStatus.COMPLETED);

        // set the payment amount to the order total price:
        payment.setAmount(order.getTotalPrice());

        // associate the payment with the order (foreign key):
        payment.setOrder(order);

        // save the payment to the database:
        paymentRepository.save(payment);
    }

    //    helper private methods:
}
