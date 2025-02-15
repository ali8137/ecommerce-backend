package com.ali.ecommerce.controller;

import com.ali.ecommerce.DTO.CreateCartDTO;
import com.ali.ecommerce.exception.CartItemException;
import com.ali.ecommerce.model.CartItem;
import com.ali.ecommerce.service.CartItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


// - Using @CrossOrigin annotation on Controller level:
// @CrossOrigin
// @CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/cart-item")
@RequiredArgsConstructor
@Slf4j
public class CartItemController {


    private final CartItemService service;

//    @Autowired
//    public CartItemController(CartItemService service) {
//        this.service = service;
//    }


//  - Method level CORS configuration:
//    @CrossOrigin(origins = "http://localhost:5173")
//    public void method1() {}



    /* TODO: developer-constraint: a CartItemException propagates from the service layer
        to the below method
        */
    //    @CrossOrigin
    @PutMapping("/increment-cart-item/{id}")
    public ResponseEntity<String>  incrementItem(
            @PathVariable("id") Long cartItemId
//            @Valid @RequestBody CartItem updatedCartItem
    ) /*throws CartItemException*/ {

        log.info("cartItemId: {}", cartItemId);
//        log.info("cartItem: {}", updatedcartItem);

        //    delegating the functionality to the corresponding Service method...

        service.incrementCartItem(cartItemId/*, updatedcartItem*/);

        return new ResponseEntity<>("cart item updated successfully", HttpStatus.CREATED);

    }


    /* TODO: developer-constraint: a CartItemException propagates from the service layer
        to the below method
        */
    //    @CrossOrigin
    @PutMapping("/decrement-cart-item/{id}")
    public ResponseEntity<String>  decrementItem(
            @PathVariable("id") Long cartItemId
//            @Valid @RequestBody CartItem updatedCartItem
    ) /*throws CartItemException*/ {

        log.info("cartItemId: {}", cartItemId);
//        log.info("cartItem: {}", updatedcartItem);

        //    delegating the functionality to the corresponding Service method...

        service.decrementCartItem(cartItemId/*, updatedcartItem*/);

        return new ResponseEntity<>("cart item updated successfully", HttpStatus.CREATED);

    }



    /* TODO: developer-constraint: a CartItemException propagates from the service layer
    to the below method
    */
    //    @CrossOrigin
    @DeleteMapping("/remove-cart-item/{id}")
    public ResponseEntity<String>  removeCartItem(
            @PathVariable("id") Long cartItemId
    ) /*throws CartItemException*/ {

        log.info("cartItemId: {}", cartItemId);

        //    delegating the functionality to the corresponding Service method...

        service.removeCartItem(cartItemId);

        return new ResponseEntity<>("cart item deleted successfully", HttpStatus.CREATED);

    }



//    //    @CrossOrigin
//    @ResponseStatus(
//            HttpStatus.NO_CONTENT
//            //   HttpStatus.OK
//            //   HttpStatus.CREATED
//    )
//    @GetMapping("/{id}")
//    public void /*or ResponseEntity<String>*/  getMethod1(
//            @RequestParam("paramName1") ClassName1 obj1,
//            @PathVariable("id") ClassName2 obj2
//    ) {
//
//        //    delegating the functionality to the corresponding Service method...
//
//        return new ResponseEntity<>(serviceObj1.serviceMethod1() /* or any other class object instance such as String, ... */ , HttpStatus.OK);
//        //    - or
//        //      return ResponseEntity.ok("company updated successfully");
//
//    }
//
//
//    //    @CrossOrigin
//    @ResponseStatus(
//            HttpStatus.NO_CONTENT
//            //    HttpStatus.OK
//            //    HttpStatus.CREATED
//    )
//    @PostMapping()
//    public void /*or ResponseEntity<String>*/  postMethod1(@RequestBody ClassName1 obj1) {
//
//        //    delegating the functionality to the corresponding Service method...
//
//        return new ResponseEntity<>(serviceObj1.serviceMethod2() /* or any other class object instance such as String, ... */ , HttpStatus.OK);
//        //    - or
//        //      return ResponseEntity.ok("company updated successfully");
//
//    }
//
//
//    //    @CrossOrigin
//    @ResponseStatus(
//            HttpStatus.NO_CONTENT
//            //    HttpStatus.OK
//            //    HttpStatus.CREATED
//    )
//    @PutMapping("/{id}")
//    public void /*or ResponseEntity<String>*/ putMethod1(
//            @RequestBody ClassName1 obj1,
//            @PathVariable("id") ClassName2 obj2
//    ) {
//
//        //    delegating the functionality to the corresponding Service method...
//
//        return new ResponseEntity<>(serviceObj1.serviceMethod3() /* or any other class object instance such as String, ... */ , HttpStatus.OK);
//        //    - or
//        //      return ResponseEntity.ok("company updated successfully");
//
//    }
//
//
//
//    //    @CrossOrigin
//    @ResponseStatus(
//            HttpStatus.NO_CONTENT
//            //    HttpStatus.OK
//            //    HttpStatus.CREATED
//    )
//    @DeleteMapping("/{id}")
//    public void /*or ResponseEntity<String>*/ putMethod1(
//            @PathVariable("id") ClassName1 obj1
//    ) {
//
//        //    delegating the functionality to the corresponding Service method...
//
//        return new ResponseEntity<>(serviceObj1.serviceMethod4() /* or any other class object instance such as String, ... */ , HttpStatus.OK);
//        //    - or
//        //      return ResponseEntity.ok("company updated successfully");
//
//    }

}
