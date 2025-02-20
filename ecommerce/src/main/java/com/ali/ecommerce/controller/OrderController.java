package com.ali.ecommerce.controller;

import com.ali.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /* TODO: developer-constraint: a CartException propagates from the service layer
        to the below method
        */
    @PostMapping("/place-order")
    public ResponseEntity<String> placeOrder(
            @AuthenticationPrincipal UserDetails userDetails
    ) /*throws CartException*/ {
        //    delegating the functionality to the corresponding Service method...
        orderService.createOrderFromCart(userDetails);

        return new ResponseEntity<>("order placed successfully", HttpStatus.CREATED);
    }
}
