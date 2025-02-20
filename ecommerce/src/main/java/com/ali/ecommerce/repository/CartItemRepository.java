package com.ali.ecommerce.repository;

import com.ali.ecommerce.model.Cart;
import com.ali.ecommerce.model.CartItem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {


//    methods with functionalities based on its name structure:

//    JPQL queries:

    @Modifying
    @Transactional
    @Query("DELETE FROM CartItem ci WHERE ci.cart.user.email = :email")
    void deleteCartItemsByUserEmail(@Param("email") String email);

    @Modifying
    @Transactional
    @Query("DELETE FROM CartItem ci WHERE ci.cart = :cart")
    void deleteCartItemsByCart(@Param("cart") Cart cart);

//    native SQL queries: methods with functionalities based on the SQL query specified in the annotation:

}
