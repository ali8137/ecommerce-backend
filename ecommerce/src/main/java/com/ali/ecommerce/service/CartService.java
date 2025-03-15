package com.ali.ecommerce.service;


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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    /* TODO: injecting the associated services of the below repositories would have been better for
        modularity, scalability and testing. add the service classes rather than the repositories below*/
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    public List<Cart> getUserCarts(UserDetails userDetails) {
        // extract email from user details:
        String email = userDetails.getUsername();

        // get the user's cart(s) from the database:
        var userCarts = getCarts(email);

        if (userCarts.isEmpty()) {
            throw new CartException("No cart found");
        }

        // return the user's cart(s):
        return userCarts;
    }

    /* TODO: developer-constraint: you can't be used unless the method createCart() is called just before it*/
    // the below method will create a new cart item and add it to the cart:
    public void addCartItem(UserDetails userDetails, CartItem cartItem) {
        /* TODO: the parameter above is better be rather made to include the product
            id, price, and must include the chosen size and color. it must also check
            before that if the to-be-bought product has this color and size (although
            we imposed on te frontend to only show the present colors and sizes, but
            it is good also to impose this constraint on the backend also*/

        // extract email from user details:
        String email = userDetails.getUsername();

        // get the user's cart(s) from the database:
        List<Cart> carts = this.getCarts(email);

        // if no cart exists, create a new one and add to it to the cart list:
        if (carts.isEmpty()) {
            Cart cart = this.createCart(email);
            carts.add(cart);
        }

        /* TODO: the below code is repeated, and hence it might be good to dedicate a method to include this code*/
        // associate the cart item with the cart:
        Cart firstCart = carts.get(0);
        cartItem.setCart(firstCart);

        // associate the cart item with the product:
        var product = productRepository.findById(cartItem.getProduct().getId())
                .orElseThrow(() -> new ProductException("Product not found"));
        cartItem.setProduct(product);

        // add the cart item to the cart for java object graph:
        List<CartItem> cartItems = firstCart.getCartItems();
        cartItems.add(cartItem);
        firstCart.setCartItems(cartItems);

        // set the quantity of the cart item to 1:
        cartItem.setQuantity(1);

        // update the total price of the cart:
        firstCart.setTotalPrice(
                firstCart.getTotalPrice().add(product.getPrice())
        );

        // save the cart to the database:
        cartRepository.save(firstCart);
    }

    // delete all the cart items of the user cart and set the total price of this cart to zero:
    public void removeAllCarts(UserDetails userDetails) {
        // extract email from user details:
        String email = userDetails.getUsername();

        // check if the cart exists:
        List<Cart> carts = cartRepository.findAllByUserEmail(email);
        if (/*!exists*/ carts == null || carts.isEmpty()) {
            throw new CartException("No cart found");
        }

        // "delete" the cart and its associated cart items from the database:
        cartItemRepository.deleteCartItemsByCart(carts.get(0));
        // set the total price to zero is better than deleting the cart
        carts.get(0).setTotalPrice(BigDecimal.ZERO);
        cartRepository.saveAll(carts);
    }


//    helper private methods:

    private List<Cart> getCarts(String email) {
        return cartRepository.findAllByUserEmail(email);
        /* TODO: it is better to also handle the exception that might be thrown when the user is not found*/
    }

    private Cart createCart(String email) {
        // create a new cart and associate it with the user:
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException("User not found"));
        Cart cart = new Cart();
        cart.setUser(user);

        return cart;
    }
}
