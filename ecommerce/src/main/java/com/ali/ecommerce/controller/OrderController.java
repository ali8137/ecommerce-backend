package com.ali.ecommerce.controller;

import com.ali.ecommerce.model.Order;
import com.ali.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    /* TODO: developer-constraint: a OrderException propagates from the service layer
        to the below method
        */
    @GetMapping("/get-orders")
    public ResponseEntity<List<Order>>  getUserOrders(
            @AuthenticationPrincipal UserDetails userDetails
    ) /*throws OrderException*/ {

        //    delegating the functionality to the corresponding Service method...
        var userOrders = orderService.getUserOrders(userDetails);

        return new ResponseEntity<>(userOrders, HttpStatus.OK);
    }

    /* TODO: developer-constraint: a OrderException propagates from the service layer
        to the below method
        */
    @GetMapping("/user-current-order")
    public ResponseEntity<Order>  getUserCurrentOrder(
            @AuthenticationPrincipal UserDetails userDetails
    ) /*throws OrderException*/ {

        //    delegating the functionality to the corresponding Service method...
        var userCurrentOrder = orderService.getUserCurrentOrder(userDetails);

        return new ResponseEntity<>(userCurrentOrder, HttpStatus.OK);
    }

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
