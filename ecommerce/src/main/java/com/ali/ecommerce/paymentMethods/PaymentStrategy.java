package com.ali.ecommerce.paymentMethods;

import com.ali.ecommerce.DTO.CreateCheckoutSessionRequestDTO;

import java.util.Map;

public interface PaymentStrategy {
    Map<String, String> pay(CreateCheckoutSessionRequestDTO request);
}
