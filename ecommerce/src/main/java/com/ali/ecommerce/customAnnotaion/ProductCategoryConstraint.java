package com.ali.ecommerce.customAnnotaion;

import com.ali.ecommerce.customAnnotaion.validator.ProductCategoryValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ProductCategoryValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ProductCategoryConstraint {
    String message() default "product category should not be the " +
            "most child category. that is, it should " +
            "not have a parent category";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
