package com.ali.ecommerce.exception.exceptionHandler;


import com.ali.ecommerce.customAnnotaion.DescriptionAndSubCategoryConstraint;
import com.ali.ecommerce.exception.*;
import com.ali.ecommerce.exception.errorResponse.ProductErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

//@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductException.class)
    public ResponseEntity<ProductErrorResponse> handleProductException(ProductException exception) {
        ProductErrorResponse response = new ProductErrorResponse(
                exception.getMessage(),
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CategoryException.class)
    public ResponseEntity<ProductErrorResponse> handleCategoryException(CategoryException exception) {
        ProductErrorResponse response = new ProductErrorResponse(
                exception.getMessage(),
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
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

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

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

                errors.put(property.isEmpty() ? "error" : property, message);
            }

        }

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomJsonException.class)
    public ResponseEntity<String> handleCustomJsonException(CustomJsonException exception) {
        return new ResponseEntity<>("error: " + exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ProductErrorResponse> handleUserException(UserException exception) {
        ProductErrorResponse response = new ProductErrorResponse(
                exception.getMessage(),
                LocalDateTime.now(),
                HttpStatus.FOUND
        );

        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @ExceptionHandler(CartException.class)
    public ResponseEntity<ProductErrorResponse> handleCartException(CartException exception) {
        ProductErrorResponse response = new ProductErrorResponse(
                exception.getMessage(),
                LocalDateTime.now(),
                HttpStatus.FOUND
        );

        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @ExceptionHandler(OrderException.class)
    public ResponseEntity<ProductErrorResponse> handleOrderException(OrderException exception) {
        ProductErrorResponse response = new ProductErrorResponse(
                exception.getMessage(),
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<ProductErrorResponse> handlePaymentException(PaymentException exception) {
        ProductErrorResponse response = new ProductErrorResponse(
                exception.getMessage(),
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(StripePaymentException.class)
    public ResponseEntity<ProductErrorResponse> handleStripePaymentException(StripePaymentException exception) {
        ProductErrorResponse response = new ProductErrorResponse(
                exception.getMessage(),
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CartItemException.class)
    public ResponseEntity<ProductErrorResponse> handleCartItemException(CartItemException exception) {
        ProductErrorResponse response = new ProductErrorResponse(
                exception.getMessage(),
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ProductErrorResponse> handleExpiredJwtException(ExpiredJwtException exception) {
        ProductErrorResponse response = new ProductErrorResponse(
                exception.getMessage(),
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}