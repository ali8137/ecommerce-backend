package com.ali.ecommerce.controller;

import com.ali.ecommerce.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/cart-item")
@RequiredArgsConstructor
public class CartItemController {

    private final CartItemService service;

    /* TODO: developer-constraint: a CartItemException propagates from the service layer
        to the below method
        */
    @PutMapping("/increment-cart-item/{id}")
    public ResponseEntity<String>  incrementItem(
            @PathVariable("id") Long cartItemId
    ) /*throws CartItemException*/ {
        //    delegating the functionality to the corresponding Service method...
        service.incrementCartItem(cartItemId);

        return new ResponseEntity<>(
                "cart item updated successfully",
                HttpStatus.CREATED
        );
    }


    /* TODO: developer-constraint: a CartItemException propagates from the service layer
        to the below method
        */
    @PutMapping("/decrement-cart-item/{id}")
    public ResponseEntity<String>  decrementItem(
            @PathVariable("id") Long cartItemId
    ) /*throws CartItemException*/ {
        //    delegating the functionality to the corresponding Service method...
        service.decrementCartItem(cartItemId/*, updatedcartItem*/);

        return new ResponseEntity<>("cart item updated successfully", HttpStatus.CREATED);
    }


    /* TODO: developer-constraint: a CartItemException propagates from the service layer
        to the below method
        */
    @DeleteMapping("/remove-cart-item/{id}")
    public ResponseEntity<String>  removeCartItem(
            @PathVariable("id") Long cartItemId
    ) /*throws CartItemException*/ {
        //    delegating the functionality to the corresponding Service method...
        service.removeCartItem(cartItemId);

        return new ResponseEntity<>("cart item deleted successfully", HttpStatus.CREATED);
    }
}
