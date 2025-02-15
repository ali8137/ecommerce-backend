package com.ali.ecommerce.webhook.stripe;

import com.ali.ecommerce.exception.PaymentException;
import com.ali.ecommerce.exception.StripePaymentException;
import com.ali.ecommerce.repository.OrderRepository;
import com.ali.ecommerce.repository.PaymentRepository;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StripeWebhookService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private static final String webhookSecret = "whsec_58d9ffc027316f2ad7d1301aec1a2d40402dcd2703ef75f260aa1f1538d43f69";

//    @Autowired
//    public PaymentService(PaymentRepository paymentRepository, OrderRepository orderRepository) {
//        this.paymentRepository = paymentRepository;
//        this.orderRepository = orderRepository;
//    }

//    public ClassName1 method1(ParameterClass1 obj1) {
//
//        //    business logic
//        //    database operations
//        //    file operations
//        //    network operations
//        //    data validation
//        //    data transformation
//        //    DTO-to-class conversion
//        //    class-to-DTO conversion
//        //    event-driven handling
//        //    email notification sending
//        //    caching
//        //    security-related operations (like JWT token generation, password encryption, etc.)
//        //    AI integration
//        //    exception handling
//        //    logging
//
//    }

    public void handleStripeWebhook(String payload, String sigHeader) {

        log.info("stripe webhook received: {}, {}", payload, sigHeader);

//        String endpointSecret = "";

        Event event;

        try {
//            Event event = Webhook.constructEvent(payload, sigHeader, webhookSecret);
            event = Webhook.constructEvent(payload, sigHeader, webhookSecret);

//            if (event.getType().equals("checkout.session.completed")) {
//                Session session = (Session) event.getDataObjectDeserializer().getObject()
//                        .orElse(null);
//            }
//
//            // add the payment to the database, and set the order status to "paid"

        } catch (SignatureVerificationException /* or exception */ e) {
            throw new StripePaymentException("webhook error: " + e.getMessage());
        }

        if (event.getType().equals("checkout.session.completed")) {
            Session session = (Session) event.getDataObjectDeserializer().getObject()
                    .orElse(null);
            //   down-casting from Object to Session

            if (session != null) {
//                Long orderId = Long.parseLong(session.getMetadata().get("orderId"));
                Long orderId = Long.valueOf(session.getMetadata().get("orderId"));

                // add the payment to the database, and set the order status to "paid", and remover the cart from the database
            }


        }

    }


//    helper private methods:
}
// > Done! The Stripe CLI is configured for your account with account id acct_1QkOwoBudHjmKGwC
//> Ready! You are using Stripe API Version [2024-12-18.acacia]. Your webhook
// signing secret is whsec_58d9ffc027316f2ad7d1301aec1a2d40402dcd2703ef75f260aa1f1538d43f69
