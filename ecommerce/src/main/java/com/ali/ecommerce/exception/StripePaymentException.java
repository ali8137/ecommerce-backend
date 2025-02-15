package com.ali.ecommerce.exception;

import java.util.Map;

public class StripePaymentException extends RuntimeException {
    public StripePaymentException(String message) {
        super(message);
    }
}
