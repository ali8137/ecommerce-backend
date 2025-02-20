package com.ali.ecommerce.controller;

import com.ali.ecommerce.DTO.CreateCheckoutSessionRequestDTO;
import com.ali.ecommerce.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService service;

    @PostMapping("/create-checkout-session")
    public ResponseEntity<Map<String, String>> createCheckoutSession(
            @RequestBody CreateCheckoutSessionRequestDTO requestBody
    ) {
        //    delegating the functionality to the corresponding Service method...
        Map<String, String> response = service.createCheckoutSession(requestBody);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
