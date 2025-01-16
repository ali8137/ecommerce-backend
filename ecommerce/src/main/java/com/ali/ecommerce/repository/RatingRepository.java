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
        //    wrapping Page inside Optional is unnecessary, because page can never be null
        //    the first parameter should be written above; otherwise running of the application will fail

    //    JPQL queries:

    //    native SQL queries:





//  - stored procedures, functions, triggers, views, ... must be created in the database and not here.
//  - in case you need to execute a complex SQL query that results in a table which is not mapped to
//    any entity class, then don't use spring data JPA and use instead either EntityManager or JdbcTemplate.
//    also, when the query is applied on a table that is not an entity class like a temporary table, ... then it is
//    better to either use EntityManager or JdbcTemplate
}
