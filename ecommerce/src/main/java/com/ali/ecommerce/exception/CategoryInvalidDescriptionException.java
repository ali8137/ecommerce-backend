package com.ali.ecommerce.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import java.util.Set;

public class CategoryInvalidDescriptionException extends ConstraintViolationException {
    public CategoryInvalidDescriptionException(String message, Set<ConstraintViolation<?>> constraintViolations) {
        super(message, constraintViolations);
    }
}
