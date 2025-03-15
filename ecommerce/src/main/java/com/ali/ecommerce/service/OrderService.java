package com.ali.ecommerce.service;


import com.ali.ecommerce.exception.CartException;
import com.ali.ecommerce.exception.OrderException;
import com.ali.ecommerce.model.Cart;
import com.ali.ecommerce.model.Order;
import com.ali.ecommerce.model.OrderItem;
import com.ali.ecommerce.model.OrderStatus;
import com.ali.ecommerce.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private final OrderRepository orderRepository;
    private final CartService cartService;



    public List<Order> getUserOrders(UserDetails userDetails) {
        // extract email from user details:
        String email = userDetails.getUsername();

        var userOrders = orderRepository.findAllByUserEmail(email);

        if (userOrders.isEmpty()) {
            throw new OrderException("No order found");
        }

        return userOrders;
    }

    // get the user's current order:
    public Order getUserCurrentOrder(UserDetails userDetails) {
        // extract email from user details:
        String email = userDetails.getUsername();

        // get the user's current order from the database and return it:
        return orderRepository
//                .findFirstByUserEmailAndOrderStatusOrderByOrderDateDesc(email, OrderStatus.CREATED)
//                or:
                .findByUserEmailAndOrderStatus(email, OrderStatus.CREATED)
                .orElseThrow(() -> new OrderException("No order found"));
    }


    // create an order from the user's cart and save it to the database:
    public void createOrderFromCart(UserDetails userDetails) {
        // get the user's cart(s) from the database:
        List<Cart> carts = cartService.getUserCarts(userDetails);
        if (carts == null || carts.isEmpty()) {
            throw new CartException("Cart is empty!");
        }
        Cart firstCart = carts.get(0);

        // create the order and set its attributes from the cart:
        Order order = Order.builder()
                .totalPrice(firstCart.getTotalPrice())
                .orderDate(LocalDateTime.now())
                .orderStatus(OrderStatus.CREATED)
                .user(firstCart.getUser())
                        .build();

        // create order items from the cart items:
        List<OrderItem> orderItems = firstCart.getCartItems().stream()
                .map(cartItem -> {

                    // create each order item from the corresponding cart item:
                    OrderItem orderItem = new OrderItem();
                    orderItem.setProduct(cartItem.getProduct());
                    orderItem.setQuantity(cartItem.getQuantity());
                    orderItem.setPrice(cartItem.getProduct().getPrice());
                    orderItem.setColor(cartItem.getColor());
                    orderItem.setSize(cartItem.getSize());

                    // associate the order item with the order:
                    orderItem.setOrder(order);

                    return orderItem;
                }).collect(Collectors.toList());

        // set the order items in the order:
        order.setOrderItems(orderItems);

//        log.info("order: {}", order);

        // save the order along with the order items in the database:
        orderRepository.save(order);

        // Empty cart (remove its items and set its total price to zero) after order is placed:
        cartService.removeAllCarts(userDetails);
    }


//    helper private methods:

}
