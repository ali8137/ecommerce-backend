package com.ali.ecommerce.service;


import com.ali.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

//    @Autowired
//    public ProductService(ProductRepository productRepository) {
//        this.productRepository = productRepository;
//    }

    public String getAllProducts() {
        //    business logic
        //    database operations
        //    file operations
        //    network operations
        //    data validation
        //    data transformation
        //    DTO-to-class conversion
        //    class-to-DTO conversion
        //    event-driven handling
        //    email notification sending
        //    caching
        //    security-related operations (like JWT token generation, password encryption, etc.)
        //    AI integration
        //    exception handling
        //    logging

        return repository.findAll().orElseThrow(() -> new ProductNotFoundException());
    }



    public ClassName1 method1(ParameterClass1 obj1) {

        //    business logic
        //    database operations
        //    file operations
        //    network operations
        //    data validation
        //    data transformation
        //    DTO-to-class conversion
        //    class-to-DTO conversion
        //    event-driven handling
        //    email notification sending
        //    caching
        //    security-related operations (like JWT token generation, password encryption, etc.)
        //    AI integration
        //    exception handling
        //    logging

    }

    public ClassName2 method2(ParameterClass2 obj2) {
        //    business logic
        //    database operations
        //    file operations
        //    network operations
        //    data validation
        //    data transformation
        //    DTO-to-class conversion
        //    class-to-DTO conversion
        //    event-driven handling
        //    email notification sending
        //    caching
        //    security-related operations (like JWT token generation, password encryption, etc.)
        //    AI integration
        //    exception handling
        //    logging
    }


//    helper private methods:

    private ClassName3 helperMethod1(ParameterClass3 obj3) {

    }

}
