package com.ali.ecommerce.repository;


import com.ali.ecommerce.model.Cart;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

//    methods with functionalities based on its name structure:

    List<Cart> findAllByUserEmail(String userEmail);

    boolean existsByUserEmail(String userEmail);

    Cart findByCartItems_Id(Long cartItemId);

//    JPQL queries:

    @Modifying
    @Transactional
    @Query("DELETE FROM Cart c WHERE c.user.email = :email")
    void deleteAllByUserEmail(@Param("email") String email);

//    native SQL queries: methods with functionalities based on the SQL query specified in the annotation:

}
