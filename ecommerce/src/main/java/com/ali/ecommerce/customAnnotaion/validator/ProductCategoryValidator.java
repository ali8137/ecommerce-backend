package com.ali.ecommerce.customAnnotaion.validator;

import com.ali.ecommerce.customAnnotaion.ProductCategoryConstraint;
import com.ali.ecommerce.model.Product;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ProductCategoryValidator implements ConstraintValidator<ProductCategoryConstraint, Product> {
    @Override
    public void initialize(ProductCategoryConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Product product, ConstraintValidatorContext constraintValidatorContext) {
        return product.getCategory().getSubCategories() == null ||
                product.getCategory().getSubCategories().isEmpty();
    }
}
