package com.ali.ecommerce.customAnnotaion;


import com.ali.ecommerce.customAnnotaion.validator.DescriptionAndSubCategoryValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Constraint(validatedBy = DescriptionAndSubCategoryValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DescriptionAndSubCategoryConstraint {
    String message() default "description should be null if the subcategory is not null";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}