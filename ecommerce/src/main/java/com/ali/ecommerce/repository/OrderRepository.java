package com.ali.ecommerce.repository;

import com.ali.ecommerce.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

//    methods with functionalities based on its name structure:

//    JPQL queries:

//    native SQL queries: methods with functionalities based on the SQL query specified in the annotation:
}
