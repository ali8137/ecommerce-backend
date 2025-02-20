package com.ali.ecommerce.service;


import com.ali.ecommerce.exception.CartItemException;
import com.ali.ecommerce.model.Cart;
import com.ali.ecommerce.model.CartItem;
import com.ali.ecommerce.repository.CartItemRepository;
import com.ali.ecommerce.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    /* TODO: injecting the associated services of the below repositories would have been better for
        modularity, scalability and testing. add the service classes rather than the repositories below*/
    private final CartRepository cartRepository;

    public void incrementCartItem(Long cartItemId) {
        // fetch the cart item from the database:
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new CartItemException("Cart item not found"));

        // increment the quantity of the cart item:
        cartItem.setQuantity(cartItem.getQuantity() + 1);

        /* TODO: a better approach is by adding triggers in the database to update the total price */
        /* TODO: the below code is repeated and should be refactored as a dedicated method */
        // update the total price of the cart:
        Cart cart = cartRepository.findByCartItems_Id(cartItemId);
        cart.setTotalPrice(cart.getTotalPrice()
                .add(cartItem.getProduct().getPrice()));

        // merge/update the cart and its associated cart items in the database:
        cartRepository.save(cart);
    }

    public void decrementCartItem(Long cartItemId) {
        // fetch the cart item from the database:
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new CartItemException("Cart item not found"));

        // decrement the quantity of the cart item:
        if (cartItem.getQuantity() == 1) {
            cartItemRepository.delete(cartItem);
            // repository.deleteById(cartItemId);
        }
        else cartItem.setQuantity(cartItem.getQuantity() - 1);

        /* TODO: a better approach is by adding triggers in the database to update the total price */
        /* TODO: the below code is repeated and should be refactored as a dedicated method */
        // update the total price of the cart:
        Cart cart = cartRepository.findByCartItems_Id(cartItemId);
        cart.setTotalPrice(cart.getTotalPrice()
                .subtract(cartItem.getProduct().getPrice()));

        // merge/update the cart and its associated cart items in the database:
        cartRepository.save(cart);
    }

    public void removeCartItem(Long cartItemId) {
        // fetch the cart item from the database:
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new CartItemException("Cart item not found"));

        /* TODO: a better approach is by adding triggers in the database to update the total price */
        /* TODO: the below code is repeated and should be refactored as a dedicated method */
        // update the total price of the cart:
        Cart cart = cartRepository.findByCartItems_Id(cartItemId);
        cart.setTotalPrice(cart.getTotalPrice()
                .subtract(cartItem.getProduct().getPrice()));

        // delete the cart item from the database:
        cartItemRepository.delete(cartItem);
    }


//    helper private methods:

}
