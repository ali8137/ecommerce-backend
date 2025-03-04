package com.ali.ecommerce.controller;

import com.ali.ecommerce.DTO.CreateCheckoutSessionRequestDTO;
import com.ali.ecommerce.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;


@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService service;

    @PostMapping("/{paymentMethod}")
    public ResponseEntity<Map<String, String>> makePayment(
            @Valid @RequestBody CreateCheckoutSessionRequestDTO requestBody,
            /* TODO: replace or rename the class of the above parameter to suit all payment strategies in the new design pattern*/
            /* TODO: we could have passed the amount as well. this will make the code performant*/
            @PathVariable("paymentMethod") String method
    ) {
        //    delegating the functionality to the corresponding Service method...
        Map<String, String> response = service.makePayment(requestBody, method);

        // persist the payment in the database:
        String sessionId = response.get("sessionId");
        BigDecimal amount = requestBody.getCartItems().stream()
                .map(cartItem -> cartItem.getProduct().getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        service.persistPayment(sessionId, amount, requestBody.getOrderId());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
