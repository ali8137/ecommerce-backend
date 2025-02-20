package com.ali.ecommerce.mapper;

import com.ali.ecommerce.DTO.CreateNewCategoryRequestDTO;
import com.ali.ecommerce.model.Category;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CategoryMapper {

    //    the below is not important to be present here when it comes to the business logic:
    public static CreateNewCategoryRequestDTO mapToCreateNewCategoryRequestDTO(
            List<Category> categories
    ) {
        List<String> categoryNames = new ArrayList<>();

        for (Category category : categories) {
            categoryNames.add(category.getName());
        }

        return CreateNewCategoryRequestDTO.builder()
                .categoryNames(categoryNames)
                .description(categories.get(categories.size() - 1)
                        .getDescription())
                .build();
    }

    //    version 3:
    public static List<Category> mapToCategoriesRecursive(
            CreateNewCategoryRequestDTO requestDTO
    ) {
        // concatenate the new list of categories to the existing categories
        List<Category> categories = new ArrayList<>();

        List<String> categoryNames =
                requestDTO.getCategoryNames();

        int categoryNamesSize = categoryNames.size();

        // call recursive method to add the categories
        return addCategories(
                categories,
                categoryNames,
                categoryNamesSize,
                requestDTO.getParentId(),
                requestDTO.getDescription(),
                0
        );
    }

    private static List<Category> addCategories(
            List<Category> categories,
            List<String> categoryNames,
            int categoryNamesSize,
            Long existingCategoriesParentId,
            String requestDescription,
            // passing the requestDTO would have been better than passing the above 4 parameters
            int i
    ) {

        if (categories.size() == categoryNamesSize)
//      if (i == categoryNamesSize - 1)
        {
            categories.get(categories.size() - 1)
                    .setDescription(requestDescription);

            return null;
        }


        var category = Category.builder()
                .name(categories.isEmpty() ?
                        categoryNames.get(i) : categoryNames.get(categories.size()))
                // the above could be better written as ".name(categoryNames.get(i))"
                .parentCategory(
                        categories.isEmpty() ?
                                (existingCategoriesParentId != null ?
                                        Category.builder()
                                        .id(existingCategoriesParentId)
                                        .build() : null) : categories.get(categories.size() - 1)
                )
                .build();

        categories.add(category);


        addCategories(
                categories,
                categoryNames,
                categoryNamesSize,
                existingCategoriesParentId,
                requestDescription,
                i + 1
        );

        //  version 3:
        List<Category> subCategories = new ArrayList<>();

        if (i != categories.size() - 1) {
            Category nextCategories = categories.get(i + 1);
            subCategories.add(nextCategories);
        }

        categories.get(i)
                .setSubCategories(subCategories);

        return categories;
    }
}