package com.ali.ecommerce.controller;

import com.ali.ecommerce.model.Cart;
import com.ali.ecommerce.model.CartItem;
import com.ali.ecommerce.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;


// - Using @CrossOrigin annotation on Controller level:
// @CrossOrigin
// @CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {


    private static final Logger log = LoggerFactory.getLogger(CartController.class);
    private final CartService service;

//    @Autowired
//    public CartController(CartService service) {
//        this.service = service;
//    }




//  - Method level CORS configuration:
//    @CrossOrigin(origins = "http://localhost:5173")
//    public void method1() {}




    /* TODO: documentation: for current project design, only one cart is there for a
        user. in other words, carts are not stored for the user after the order
        is placed
        */
    /* TODO: developer-constraint: a CartException propagates from the service layer
        to the below method
        */
    //    @CrossOrigin
    @GetMapping("/get-carts")
    public ResponseEntity<List<Cart>>  getUserCarts(
//            @RequestParam("username") String email
//            /* TODO: validate the
//                above parameters
//                */
            @AuthenticationPrincipal UserDetails userDetails
    ) /*throws CartException*/ {

        //    delegating the functionality to the corresponding Service method...
        log.info("userDetails: {}", userDetails);
        var userCarts = service.getUserCarts(userDetails);

//        if (userCarts.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }

        return new ResponseEntity<>(userCarts, HttpStatus.OK);
    }



//    /* finished_TODO: developer-constraint: a CartException and a UserException propagate from the service layer
//        to the below method
//        */
//    the below method was removed
//    //    @CrossOrigin
//    @PostMapping("/create-cart")
//    public ResponseEntity<String>  createCart(
//            @Valid @RequestBody CreateCartDTO requestDTO
//    ) /*throws CartException, UserException*/ {
//
//        //    delegating the functionality to the corresponding Service method...
//
//        service.createCart(requestDTO);
//
//        return new ResponseEntity<>("cart created successfully", HttpStatus.CREATED);
//
//    }


//    /* finished_TODO: documentation: for current project design, only one cart is there for a user*/
//    /* finished_TODO: developer-constraint: a CartException propagates from the service layer
//        to the below method
//        */
//    /* finished_TODO: you can rather create the below method with
//        the parameter being CreateCartItemDTO that includes the Cart id (instead
//        of User email) and the CartItem to be added
//        */
//    the below method was removed
//    //    @CrossOrigin
//    @PostMapping("/add-cart-item")
//    public ResponseEntity<String>  addCartItem(
//            @Valid @RequestBody CreateCartDTO requestDTO
//    ) /*throws CartException*/ {
//
//        //    delegating the functionality to the corresponding Service method...
//
//        service.addCartItem(requestDTO);
//
//        return new ResponseEntity<>("cart item added successfully", HttpStatus.CREATED);
//
//    }



    /* TODO: documentation: for current project design, only one cart is there for a user*/
    /* TODO: developer-constraint: a CartException propagates from the service layer
        to the below method
        */
    //    @CrossOrigin
    @PostMapping("/add-cart-item")
    public ResponseEntity</*String*/List<Cart>>  addCartItem(
            @AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody CartItem cartItem
    ) /*throws CartException*/ {

        log.info("userDetails: {}", userDetails);
        log.info("cartItem: {}", cartItem);


        //    delegating the functionality to the corresponding Service method...

        service.addCartItem(userDetails, cartItem);

        var userCarts = service.getUserCarts(userDetails);

//        return new ResponseEntity<>("cart item added successfully", HttpStatus.CREATED);
        return new ResponseEntity<>(userCarts, HttpStatus.CREATED);
//      - returning the new updated cart items list to the user after adding a new cart item can
//        be useful in the frontend side in order for the user to have the ability to access the
//        updated cart and update the UI accordingly, especially if the returned objects have a complicated
//        structure due to having relationships with other entities in the database

    }


//    /* finished_TODO: documentation: for current project design, only one cart is there for a user*/
//    /* finished_TODO: developer-constraint: a CartException propagates from the service layer
//        to the below method
//        */
//    the below method was removed
//    //    @CrossOrigin
//    @DeleteMapping("/remove-cart/{id}")
//    public ResponseEntity<String>  removeCart(
//            @PathVariable Long cartId
//    ) /*throws CartException*/ {
//
//        log.info("cartId: {}", cartId);
//
//
//        //    delegating the functionality to the corresponding Service method...
//
//        service.removeCart(cartId);
//
//        return new ResponseEntity<>("cart removed successfully", HttpStatus.CREATED);
//
//    }


    /* TODO: documentation: for current project design, only one cart is there for a user*/
    /* TODO: developer-constraint: a CartException propagates from the service layer
        to the below method
        */
    /* TODO: documentation: for the current database design, which is wrong when it comes to the
        relationship between Cart and User being one-to-many instead of
        one-to-one, we will remove all the carts (although there is always only one cart
        based on how i enforced that in my code) instead of removing a single cart
        */
    //    @CrossOrigin
    @DeleteMapping("/remove-all-carts")
    public ResponseEntity<String> removeAllCarts(
            @AuthenticationPrincipal UserDetails userDetails
    ) /*throws CartException*/ {

        //    delegating the functionality to the corresponding Service method...

        service.removeAllCarts(userDetails);

        return new ResponseEntity<>("carts removed successfully", HttpStatus.CREATED);

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
