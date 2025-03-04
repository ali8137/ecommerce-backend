package com.ali.ecommerce.paymentMethods.stripe.webhook;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook/stripe")
@RequiredArgsConstructor
public class StripeWebhookController {

    private final StripeWebhookService stripeWebhookService;

    /* TODO: test this webhook handler method*/
    @PostMapping("")
    public ResponseEntity<String> handleStripeWebhook(@RequestBody String payload,
                                                      @RequestHeader("Stripe-Signature") String sigHeader) {

        //    delegating the functionality to the corresponding Service method...
        stripeWebhookService.handleStripeWebhook(payload, sigHeader);

        return new ResponseEntity<>("stripe webhook received", HttpStatus.OK);
    }
}
