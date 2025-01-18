package com.ali.ecommerce.exception.exceptionHandler;


import com.ali.ecommerce.customAnnotaion.DescriptionAndSubCategoryConstraint;
import com.ali.ecommerce.exception.*;
import com.ali.ecommerce.exception.errorResponse.ProductErrorResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

//@ControllerAdvice
@RestControllerAdvice
//the methods inside the class annotated with this annotation will get
// executed when an exception is thrown/present (could be thrown
// previously at other layers like service layer, ...) at the controller layer
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductException.class)
    public ResponseEntity<ProductErrorResponse> handleProductException(ProductException exception)
//  - the handler method can return a void instead of a response entity, but
//    in this case the spring default error handling mechanism will generate
//    generic response itself
    {

        ProductErrorResponse response = new ProductErrorResponse(
                exception.getMessage(),
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND
                //    - the http status of the above line will be just used to
                //      get the http status in the frontend and display it
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        //        the httpStatus of the above line will be the http status in the frontend side
    }


    @ExceptionHandler(CategoryException.class)
    public ResponseEntity<ProductErrorResponse> handleCategoryException(CategoryException exception) {

        ProductErrorResponse response = new ProductErrorResponse(
//                the above name "ProductErrorResponse" should be changed either to fit
//                the Category exception, thus changing it to "CategoryErrorResponse", or should
//                be changed to fit both together and be generic, thus changing it to "ErrorResponse"
                exception.getMessage(),
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND
                //    - the http status of the above line will be just used to
                //      get the http status in the frontend and display it
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        //        the httpStatus of the above line will be the http status in the frontend side
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(
            ConstraintViolationException exception
    ) {

        Map<String, String> errors = new HashMap<>();

        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();

        for (ConstraintViolation<?> violation : violations) {
            String property = violation.getPropertyPath().toString();
            String message = violation.getMessage();

            errors.put(property.isEmpty() ? "error" : property, message);
        }

        log.error("Validation errors: {}", errors);

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }


//  - the below is just one way of targeting a specific exception/error of
//    the exception ConstraintViolationException
    @ExceptionHandler(CategoryInvalidDescriptionException.class)
    public ResponseEntity<Map<String, String>> handleCategoryInvalidDescriptionException(
            CategoryInvalidDescriptionException exception
    ) {

        Map<String, String> errors = new HashMap<>();

        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();

        for (ConstraintViolation<?> violation : violations) {

            if (violation.getConstraintDescriptor().getAnnotation() instanceof DescriptionAndSubCategoryConstraint) {
                String property = violation.getPropertyPath().toString();
                String message = violation.getMessage();

                log.info("property: {}, message: {}", property, message);
                errors.put(property.isEmpty() ? "error" : property, message);
            }

        }

        log.error("category validation errors: {}", errors);

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomJsonException.class)
    public ResponseEntity<String> handleCustomJsonException(CustomJsonException exception) {

        return new ResponseEntity<>("error: " + exception.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(UserException.class)
    public ResponseEntity<ProductErrorResponse> handleUserException(UserException exception) {

        ProductErrorResponse response = new ProductErrorResponse(
//                the above name "ProductErrorResponse" should be changed either to fit
//                the Category exception, thus changing it to "CategoryErrorResponse", or should
//                be changed to fit both together and be generic, thus changing it to "ErrorResponse"
                exception.getMessage(),
                LocalDateTime.now(),
                HttpStatus.FOUND
                //    - the http status of the above line will be just used to
                //      get the http status in the frontend and display it
        );

        return new ResponseEntity<>(response, HttpStatus.FOUND);
        //        the httpStatus of the above line will be the http status in the frontend side
    }
}
