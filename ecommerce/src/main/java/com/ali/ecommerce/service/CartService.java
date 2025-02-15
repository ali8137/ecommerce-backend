package com.ali.ecommerce.service;


import com.ali.ecommerce.DTO.CreateCartDTO;
import com.ali.ecommerce.exception.CartException;
import com.ali.ecommerce.exception.ProductException;
import com.ali.ecommerce.exception.UserException;
import com.ali.ecommerce.model.Cart;
import com.ali.ecommerce.model.CartItem;
import com.ali.ecommerce.model.User;
import com.ali.ecommerce.repository.CartItemRepository;
import com.ali.ecommerce.repository.CartRepository;
import com.ali.ecommerce.repository.ProductRepository;
import com.ali.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private static final Logger log = LoggerFactory.getLogger(CartService.class);
    private final CartRepository cartRepository;
//  - injecting the associated services of the below repositories would have
//    been better for modularity, scalability and testing:
    /* TODO: add the service classes rather than the repositories below*/
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;


//    @Autowired
//    public CartService(CartRepository cartRepository, UserRepository userRepository) {
//        this.cartRepository = cartRepository;
//        this.userRepository = userRepository;
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


    public List<Cart> getUserCarts(UserDetails userDetails) {

        // extract email from user details:
        String email = userDetails.getUsername();

        var userCarts = getCarts(email);

        if (userCarts.isEmpty()) {
            throw new CartException("No cart found");
        }

        return userCarts;
    }

//    public void createCart(CreateCartDTO requestDTO) {
//        /* TODO: the parameter above is better be rather made to include the product
//            id, price, and must include the chosen size and color. it must also check
//            before that if the to-be-bought product has this color and size (although
//            we imposed on te frontend to only show the present colors and sizes, but
//            it is good also to impose this constraint on the backend also
//            */
//
//////        first version ---- beginning
////        User user = userRepository.findByEmail(requestDTO.getEmail())
////                .orElseThrow(() -> new CartException("User not found"));
////
////        if (cartRepository.existsByUserEmail(requestDTO.getEmail())) {
////            throw new CartException("a cart already exists");
////        }
////
////        Cart cart = new Cart();
////        cart.setUser(user);
//////        the above is to set the foreign key of the cart to the user
////
////        var cartItem = requestDTO.getCartItem();
////        cartItem.setCart(cart);
//////        the above is to set the foreign key of the cartItem to the cart
////
////
////        cart.setCartItems(List.of(cartItem));
//////        the above was done in order to enable cascade persistence (along with
//////        "cascade= CASCADE_ALL" property), and also to have a consistent java object graph
////
////
////        cartRepository.save(cart);
//////        first version ---- end
//
//
//
////        second version ---- beginning
//        // fetch the user's carts from the database, and take the first cart:
//        Optional<Cart> cartOptional = cartRepository.findAllByUserEmail(requestDTO.getEmail())
//                .stream()
//                .findFirst();
//
//        cartOptional.ifPresent((cart) -> {
//            throw new CartException("a cart already exists");
//        });
//
//        //    if no cart exists, create a new one:
//        cartOptional.orElseGet(() -> {
//
//                Cart cart = this.createCart(requestDTO.getEmail());
//
//                /* TODO: the below code is repeated, and hence it might be good to
//                    dedicate a method to include this code
//                    */
//                var cartItem = requestDTO.getCartItem();
//                cartItem.setCart(cart);
//                /* TODO: cartItem.setProduct();
//                   */
//                //   the above is to set the foreign key of the cartItem to the cart
//
//                cart.setCartItems(List.of(cartItem));
//                //   the above was done in order to enable cascade persistence (along with
//                //   "cascade= CASCADE_ALL" property), and also to have a consistent java object graph
//
////          - a better approach than the below one is to add a trigger in the database rather
////            than adding the following code in the service layer
//                cart.setTotalPrice(cart.getTotalPrice().add(cartItem.getPrice()));
//                log.info("cart total price: {}", cart.getTotalPrice());
//
//                cartRepository.save(cart);
//
//                return cart;
//            });
////        second version ---- end
//
//
//    }



//    public void addCartItem(CreateCartDTO requestDTO) {
//        /* TODO: the parameter above is better be rather made to include the product
//            id, price, and must include the chosen size and color. it must also check
//            before that if the to-be-bought product has this color and size (although
//            we imposed on te frontend to only show the present colors and sizes, but
//            it is good also to impose this constraint on the backend also
//            */
//
////        List<Cart> cart = cartRepository.findAllByUserEmail(requestDTO.getEmail());
////
////        if (cart.isEmpty()) {
////            throw new CartException("No cart found");
////        }
//        List<Cart> carts = this.getUserCarts(requestDTO.getEmail());
//
//        /* TODO: the below code is repeated, and hence it might be good to
//            dedicate a method to include this code
//            */
//        CartItem cartItem = requestDTO.getCartItem();
//        Cart firstCart = carts.get(0);
//        cartItem.setCart(firstCart);
//        /* TODO: cartItem.setProduct();
//         */
//
//        firstCart.addCartItem(cartItem);
//
//        firstCart.setTotalPrice(
//                firstCart.getTotalPrice().add(cartItem.getPrice())
//        );
//
//        cartRepository.save(firstCart);
//    }



    /* TODO: developer-constraint: you can't be used unless the method
        createCart() is called just before it
        */
    // the below method will create a new cart item and add it to the cart:
    public void addCartItem(UserDetails userDetails, CartItem cartItem) {
        /* TODO: the parameter above is better be rather made to include the product
            id, price, and must include the chosen size and color. it must also check
            before that if the to-be-bought product has this color and size (although
            we imposed on te frontend to only show the present colors and sizes, but
            it is good also to impose this constraint on the backend also
            */

        log.info("userDetails: {}", userDetails);
        log.info("cartItem: {}", cartItem);

        // extract email from user details:
        String email = userDetails.getUsername();

//        List<Cart> cart = cartRepository.findAllByUserEmail(email);
//
//        if (cart.isEmpty()) {
//            throw new CartException("No cart found");
//        }
        // get the user's cart(s) from the database:
        List<Cart> carts = this.getCarts(email);


        // if no cart exists, create a new one and add to it to the cart list:
        if (carts.isEmpty()) {
            Cart cart = this.createCart(email);
            carts.add(cart);
        }

        /* TODO: the below code is repeated, and hence it might be good to
            dedicate a method to include this code
            */
        // associate the cart item with the cart:
        Cart firstCart = carts.get(0);
        cartItem.setCart(firstCart);
//        foreign key

        // associate the cart item with the product:
        var product = productRepository.findById(cartItem.getProduct().getId())
                .orElseThrow(() -> new ProductException("Product not found"));
        cartItem.setProduct(product);
//        foreign key

        // add the cart item to the cart for java object graph:
//        List<CartItem> cartItems = List.of(cartItem);
//      - the above is wrong because the above list (List.of(...)) is immutable, and
//        hence hibernate won't be able to change this list (like for example applying clear() method, ...)
//        List<CartItem> cartItems = new ArrayList<>();
////      - better not to do the above, as this may lead to hibernate removal of the
////        previous cartItems present in the cart in the database, in case
////        orphanRemoval is set to true
        List<CartItem> cartItems = firstCart.getCartItems();
        cartItems.add(cartItem);
        firstCart.setCartItems(cartItems);
//        java object graph

        // set the quantity of the cart item to 1:
        cartItem.setQuantity(1);

        // update the total price of the cart:
        firstCart.setTotalPrice(
                firstCart.getTotalPrice().add(product.getPrice())
        );

        // log the cart:
        log.info("cart: {}", firstCart);

        // save the cart to the database:
        cartRepository.save(firstCart);
    }


//    public void removeCart(Long cartId) {
//        // check if the cart exists:
//        Cart cart = cartRepository.findById(cartId)
//                .orElseThrow(() -> new CartException("Cart not found"));
//
//        // delete the cart from the database:
//        cartRepository.deleteById(cartId);
//    }

    // delete all the cart items of the user cart and set the total price of this cart to zero:
    public void removeAllCarts(UserDetails userDetails) {
        // extract email from user details:
        String email = userDetails.getUsername();

        // check if the cart exists:
//        boolean exists = cartRepository.existsByUserEmail(email);
        List<Cart> carts = cartRepository.findAllByUserEmail(email);
        if (/*!exists*/ carts == null || carts.isEmpty()) {
            throw new CartException("No cart found");
        }

        // delete the cart and its associated cart items from the database:
//        cartItemRepository.deleteCartItemsByUserEmail(email);
        cartItemRepository.deleteCartItemsByCart(carts.get(0));
//        cartRepository.deleteAllByUserEmail(email);
        carts.get(0).setTotalPrice(BigDecimal.ZERO);
//      - setting the total price to zero is better than deleting the cart
//      - the above two methods are JPQL ~queries, and hence why we also needed to
//        manually delete the cart items. as JPQL queries don't support cascading
        cartRepository.saveAll(carts);
    }


//    helper private methods:

    private List<Cart> getCarts(String email) {
        log.info("carts: {}", cartRepository.findAllByUserEmail(email));
        return cartRepository.findAllByUserEmail(email);
        /* TODO: it is better to also handle the exception that might be
            thrown when the user is not found
            */
    }

    private Cart createCart(String email) {
        // create a new cart and associate it with the user:
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException("User not found"));
        Cart cart = new Cart();
        cart.setUser(user);
        //   the above is to set the foreign key of the cart to the user

//        return cartRepository.save(cart);
        return cart;
    }
}
