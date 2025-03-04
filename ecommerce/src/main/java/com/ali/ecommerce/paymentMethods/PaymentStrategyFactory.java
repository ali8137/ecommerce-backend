package com.ali.ecommerce.paymentMethods;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PaymentStrategyFactory {
    private final Map<String, PaymentStrategy> paymentStrategies = new HashMap<>();

    @Autowired
    public PaymentStrategyFactory(List<PaymentStrategy> paymentStrategies) {
        // add all registered payment strategies beans to the strategies map of this factory class:
        paymentStrategies.forEach(paymentStrategy ->
                this.paymentStrategies.put(
                        paymentStrategy.getClass().getSimpleName().toLowerCase(),
                        paymentStrategy
                )
        );
    }
    public PaymentStrategy getPaymentStrategy(String paymentMethod) {
        PaymentStrategy strategy = paymentStrategies.get(paymentMethod.toLowerCase() + "payment");

        if (strategy == null) {
            throw new IllegalArgumentException("Invalid payment method: " + paymentMethod);
        }

        return strategy;
    }
}
