package com.ali.ecommerce.mapper;

import com.ali.ecommerce.DTO.CreateNewCategoryRequestDTO;
import com.ali.ecommerce.model.Category;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CategoryMapper {

//    private final CategoryRepository categoryRepository;
////    it is not a good practice to couple the mapper class with the repository class

//    public CategoryMapper(CategoryRepository categoryRepository) {
//        this.categoryRepository = categoryRepository;
//    }

    //    the below is not important to be present here when it comes to the business logic:
    public static CreateNewCategoryRequestDTO mapToCreateNewCategoryRequestDTO(List<Category> categories) {

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

//    public List<Category> mapToCategories(
//            CreateNewCategoryRequestDTO createNewCategoryRequestDTO
//    )
////          - the above method better be static to prevent tight
////            coupling between service or repository class and this mapper class
//    {
//        List<Category> categories = new ArrayList<>();
//
//        List<String> categoryNames =
//                createNewCategoryRequestDTO.getCategoryNames();
//
//        int categoryNamesSize = categoryNames.size();
//
//        int j = 0;
//        boolean isCategoryExists = true;
//        for (j = 0; j < categoryNamesSize - 1; j++) {
//            isCategoryExists = categoryRepository.
//                    existsByName(categoryNames.get(j));
////          - it is better to find another way where the repository method
////            is not used/called inside this mapper class in order to prevent
////            tight coupling and to separate the concerns between the mapper
////            class and the repository class
//
//            if (!isCategoryExists) {
//                break;
//            }
//        }
//
//        if (isCategoryExists) {
//            throw new CategoryException("Category already exists");
//        }
//
//        for (int i = j; i < categoryNamesSize; i++) {
//            var category = Category.builder()
//                    .name(categoryNames.get(i))
//                    .parentCategory(i == 0 ? null : categories.get(i - 1))
//                    .build();
//
//            categories.add(category);
//        }
//
//        for (int i = j; i < categories.size(); i++) {
//            Category nextCategories = categories.get(categories.size() + 1);
//            List<Category> subCategories = new ArrayList<>();
//            subCategories.add(nextCategories);
//
//            categories.get(i)
//                    .setSubCategory(
//                            categories.indexOf(categories.get(i)) == categories.size() - 1 ? null : subCategories
//                    );
//        }
//
//        return categories;
//
//    }


////    version 2:
//    public static List<Category> mapToCategories(
//            CreateNewCategoryRequestDTO requestDTO
//    ) {
//        List<Category> categories = new ArrayList<>();
//
//        List<String> categoryNames =
//                requestDTO.getCategoryNames();
//
//        int categoryNamesSize = categoryNames.size();
//
//        for (int i = 0; i < categoryNamesSize; i++) {
//            var category = Category.builder()
//                    .name(categoryNames.get(i))
//                    .parentCategory(
//                            i == 0 ?
//                                    Category.builder()
//                                            .id(requestDTO.getParentId())
//                                            .build() : categories.get(i - 1)
//                    )
//                    .build();
//
//            categories.add(category);
//        }
//
//        for (int i = 0; i < categories.size(); i++) {
//            Category nextCategory = categories.get(i + 1);
//            List<Category> subCategories = new ArrayList<>();
//            subCategories.add(nextCategory);
//
//            categories.get(i)
//                    .setSubCategory(
//                            categories.indexOf(categories.get(i)) == categories.size() - 1 ?
//                                    null : subCategories
//                    );
//        }
//
////        categories.get(0)
////                .setParentCategory(
////                        Category.builder()
////                                .id(requestDTO.getParentId())
////                                .build()
////                );
//
//        return categories;
//    }


    //    version 3:
    public static List<Category> mapToCategoriesRecursive(
            CreateNewCategoryRequestDTO requestDTO
    ) {

//        concatenate the new list of categories to the existing categories
        List<Category> categories = new ArrayList<>();

        List<String> categoryNames =
                requestDTO.getCategoryNames();

        int categoryNamesSize = categoryNames.size();

//        call recursive method to add the categories
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
//          - passing the requestDTO would have been better than
//            passing the above 4 parameters
            int i
    ) {

        if (categories.size() == categoryNamesSize)
//        or:
//            (i == categoryNamesSize - 1)
        {
            categories.get(categories.size() - 1)
                    .setDescription(requestDescription);

            return null;
        }


        var category = Category.builder()
                .name(categories.isEmpty() ?
                        categoryNames.get(i) : categoryNames.get(categories.size()))
//                the above could be better written as ".name(categoryNames.get(i))"
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


//        //  version 1:
//        Category nextCategories = i == categories.size() - 1 ? null : categories.get(i + 1);
//        List<Category> subCategories = new ArrayList<>();
//        subCategories.add(nextCategories);
//
//        categories.get(i)
//                .setSubCategories(subCategories);

//        //  version 2:
//        if (i == categories.size() - 1) {
//            Category nextCategories = categories.get(i + 1);
//            List<Category> subCategories = new ArrayList<>();
//            subCategories.add(nextCategories);
//
//            categories.get(i)
//                    .setSubCategories(subCategories);
//        }

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
