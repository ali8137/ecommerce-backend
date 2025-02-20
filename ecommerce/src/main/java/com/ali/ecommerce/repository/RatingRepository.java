package com.ali.ecommerce.repository;

import com.ali.ecommerce.model.Product;
import com.ali.ecommerce.model.Rating;
import com.ali.ecommerce.model.RatingValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

//    method-name-based queries:

        Page<Rating> findAllByRatingValue(RatingValue ratingValue, Pageable pageable);

//    JPQL queries:

//    native SQL queries:
}
