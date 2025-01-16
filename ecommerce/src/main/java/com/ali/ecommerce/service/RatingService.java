package com.ali.ecommerce.service;


import com.ali.ecommerce.model.Rating;
import com.ali.ecommerce.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository repository;

//    @Autowired
//    public RatingService(RatingRepository repository) {
//        this.repository = repository;
//    }

//    public List<Rating> getRatingsPerProduct(Long productId) {
//        /* TODO: features: you can also implement here the feature of showing a fixed number of
//            ratings, and then the user will choose to retrieve more elements either by
//            scrolling down or by clicking on "load more" button*/
//
//        //    business logic
//        //    database operations
//        //    file operations
//        //    network operations
//        //    data validation
//        //    data transformation
//        //    DTO-to-class conversion
//        //    class-to-DTO conversion
//        //    event-driven handling
//        //    email notification sending
//        //    caching
//        //    security-related operations (like JWT token generation, password encryption, etc.)
//        //    AI integration
//        //    exception handling
//        //    logging
//
//
//
//
//    }

//    helper private methods:


}
