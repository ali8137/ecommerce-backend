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
//        the above list of payment strategies is injected from the Spring
//        context (constructor injection). and then we can set the value (value
//        of elements in this case) of the above data field to point to this injected
//        dependency (elements of the injected dependency in this case).
//        this.paymentStrategies = new HashMap<>();

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
