package com.ali.ecommerce.repository;

import com.ali.ecommerce.model.Order;
import com.ali.ecommerce.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUserEmail(String email);

    Optional<Order> findFirstByUserEmailAndOrderStatusOrderByOrderDateDesc(String email, OrderStatus orderStatus);

    Optional<Order> findByUserEmailAndOrderStatus(String email, OrderStatus orderStatus);

//    methods with functionalities based on its name structure:

//    JPQL queries:

//    native SQL queries: methods with functionalities based on the SQL query specified in the annotation:
}
