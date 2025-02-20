package com.ali.ecommerce.config;

import com.stripe.Stripe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/* TODO: we can remove the below class because we accessed the key inside the service class directly*/
@Configuration
public class StripeConfig {

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    public StripeConfig() {
        Stripe.apiKey = stripeApiKey;
    }

}
