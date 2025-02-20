package com.ali.ecommerce.customAnnotaion.validator;

import com.ali.ecommerce.customAnnotaion.DescriptionAndSubCategoryConstraint;
import com.ali.ecommerce.model.Category;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class DescriptionAndSubCategoryValidator implements ConstraintValidator<DescriptionAndSubCategoryConstraint, Category> {
    @Override
    public void initialize(DescriptionAndSubCategoryConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Category category, ConstraintValidatorContext constraintValidatorContext) {
        if (
                category.getDescription() == null &&
                        (category.getSubCategories() == null || category.getSubCategories().isEmpty())
        ) {
            return false;
        };
        return true;
    }
}
