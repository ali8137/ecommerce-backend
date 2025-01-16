package com.ali.ecommerce.customAnnotaion.validator;

import com.ali.ecommerce.customAnnotaion.DescriptionAndSubCategoryConstraint;
import com.ali.ecommerce.model.Category;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

//- it would have been better to name the below class as CategoryDescriptionAndSubCategoryValidator
//   and also to accordingly modify the name of annotation class. this will be more specific
@Slf4j
public class DescriptionAndSubCategoryValidator implements ConstraintValidator<DescriptionAndSubCategoryConstraint, Category>
// - the first parameter in the ConstraintValidator above is the
//   annotation associated with this validator class. and the second parameter is
//   the type of the annotated class in this case
{
    @Override
    public void initialize(DescriptionAndSubCategoryConstraint constraintAnnotation)
//    this method is to initialize the validator
    {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Category category, ConstraintValidatorContext constraintValidatorContext)
//    Category parameter represents the class annotated with this annotation @DescriptionAndSubCategoryConstraint
    {

        if (
                category.getDescription() == null &&
                        (category.getSubCategories() == null || category.getSubCategories().isEmpty())
        ) {
//            log.error("Description should be null if the subcategory is not null or empty");
//            return true;

            return false;
        };
        return true;

//      - this validation method won't throw a ConstraintViolationException
//        as regularly happens, it will just log a warning to other developers
    }


//    private String subCategory;
//
//    @Override
//    public void initialize(DescriptionAndSubCategoryConstraint constraintAnnotation) {
//        this.subCategory = constraintAnnotation.subCategory();
//    }
//
//    @Override
//    public boolean isValid(String descriptionValue, ConstraintValidatorContext constraintValidatorContext) {
//
//        if (descriptionValue == null ) {
//            return true;
//        }
//
//        Class<?> categoryEntityClass = descriptionValue.getClass();
//
//        try {
////            java retention:
//            Field subCategoryField = categoryEntityClass.getDeclaredField(this.subCategory);
////            the above throws exception "NoSuchFieldException":
//            subCategoryField.setAccessible(true);
//
//            Object subCategoryValue = subCategoryField.get(descriptionValue);
////            the above throws exception "IllegalAccessException":
//
//            return subCategoryValue == null;
////            validate only if subCategory is null
//
//        } catch (NoSuchFieldException | IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
