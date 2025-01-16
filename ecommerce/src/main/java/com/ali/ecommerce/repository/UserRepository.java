package com.ali.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ali.ecommerce.model.User;


import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
