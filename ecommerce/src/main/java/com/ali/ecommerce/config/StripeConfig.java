package com.ali.ecommerce.config;

import com.stripe.Stripe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig {

    @Value("${stripe.api.key}")
    private String stripeApiKey;
//    or:
//    private static final String stripeApiKey;
//    static {
//        stripeApiKey = System.getenv("STRIPE_SECRET_KEY");
//    }

    public StripeConfig() {
//        the @configuration class StripeConfig will be constructed when the application starts
        Stripe.apiKey = stripeApiKey;
    }

}
