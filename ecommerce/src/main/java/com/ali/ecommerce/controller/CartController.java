package com.ali.ecommerce.controller;

import com.ali.ecommerce.model.Cart;
import com.ali.ecommerce.model.CartItem;
import com.ali.ecommerce.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService service;

    /* TODO: documentation: for current project design, only one cart is there for a
        user. in other words, carts are not stored for the user after the order
        is placed
        */
    /* TODO: developer-constraint: a CartException propagates from the service layer
        to the below method
        */
    @GetMapping("/get-carts")
    public ResponseEntity<List<Cart>>  getUserCarts(
            @AuthenticationPrincipal UserDetails userDetails
    ) /*throws CartException*/ {

        //    delegating the functionality to the corresponding Service method...
        var userCarts = service.getUserCarts(userDetails);

        return new ResponseEntity<>(userCarts, HttpStatus.OK);
    }

    /* TODO: documentation: for current project design, only one cart is there for a user*/
    /* TODO: developer-constraint: a CartException propagates from the service layer
        to the below method
        */
    @PostMapping("/add-cart-item")
    public ResponseEntity<List<Cart>>  addCartItem(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody CartItem cartItem
    ) /*throws CartException*/ {
        //    delegating the functionality to the corresponding Service method...
        service.addCartItem(userDetails, cartItem);

        var userCarts = service.getUserCarts(userDetails);

        return new ResponseEntity<>(userCarts, HttpStatus.CREATED);
    }

    /* TODO: documentation: for current project design, only one cart is there for a user*/
    /* TODO: developer-constraint: a CartException propagates from the service layer
        to the below method
        */
    /* TODO: documentation: for the current database design, which is wrong when it comes to the
        relationship between Cart and User being one-to-many instead of
        one-to-one, we will remove all the carts (although there is always only one cart
        based on how i enforced that in my code) instead of removing a single cart
        */
    @DeleteMapping("/remove-all-carts")
    public ResponseEntity<String> removeAllCarts(
            @AuthenticationPrincipal UserDetails userDetails
    ) /*throws CartException*/ {
        //    delegating the functionality to the corresponding Service method...
        service.removeAllCarts(userDetails);

        return new ResponseEntity<>("carts removed successfully", HttpStatus.CREATED);
    }
}
