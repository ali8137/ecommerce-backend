package com.ali.ecommerce.repository;

import java.util.Optional;

public interface ProductRepository {
    Optional<String> findAll();
}
