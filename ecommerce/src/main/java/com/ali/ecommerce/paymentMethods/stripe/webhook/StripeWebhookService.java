package com.ali.ecommerce.paymentMethods.stripe.webhook;

import com.ali.ecommerce.exception.StripePaymentException;
import com.ali.ecommerce.repository.OrderRepository;
import com.ali.ecommerce.repository.PaymentRepository;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StripeWebhookService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private static final String webhookSecret;

    static {
        // SECRET_KEY = System.getProperty("jwt.secret");
        webhookSecret = System.getenv("STRIPE_WEBHOOK_SECRET");
    }

    public void handleStripeWebhook(String payload, String sigHeader) {
//        String endpointSecret = "";
        Event event;

        try {
            event = Webhook.constructEvent(payload, sigHeader, webhookSecret);

            /* TODO: add the payment to the database, set the order status to "paid", and remove the cart from the database*/
        } catch (SignatureVerificationException /* or exception */ e) {
            throw new StripePaymentException("webhook error: " + e.getMessage());
        }

        if (event.getType().equals("checkout.session.completed")) {
            Session session = (Session) event.getDataObjectDeserializer().getObject()
                    .orElse(null);
            // down-casting from Object to Session

            if (session != null) {
                Long orderId = Long.valueOf(session.getMetadata().get("orderId"));
                // Long orderId = Long.parseLong(session.getMetadata().get("orderId"));

                // add the payment to the database, and set the order status to "paid", and remove the cart from the database
            }


        }

    }


//    helper private methods:
}