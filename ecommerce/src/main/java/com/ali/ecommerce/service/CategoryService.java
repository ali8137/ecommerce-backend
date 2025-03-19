package com.ali.ecommerce.service;


import com.ali.ecommerce.DTO.databaseDTO.CategoryDTO;
import com.ali.ecommerce.DTO.CreateNewCategoryRequestDTO;
import com.ali.ecommerce.exception.CategoryException;
import com.ali.ecommerce.exception.CategoryInvalidDescriptionException;
import com.ali.ecommerce.model.Category;
import com.ali.ecommerce.repository.CategoryRepository;
import com.ali.ecommerce.util.CategoryHierarchyTree;
import com.ali.ecommerce.util.CategoryNode;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

//import static com.ali.ecommerce.mapper.CategoryMapper.mapToCategories;
import static com.ali.ecommerce.mapper.CategoryMapper.mapToCategoriesRecursive;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryDTO> getAllCategories() {
        var categories = categoryRepository.findAllCategoryHierarchies().stream()
                .map(row ->
                {
                        return new CategoryDTO(
                        (Long) row[0],
                        (String) row[1]
                        );
                }
                    )
                .toList();

        if (categories.isEmpty()) {
            throw new CategoryException("no categories found");
        }

        return categories;
    }

    /* TODO: develop the below method using Criteria API instead of @Query*/
    // public List<CategoryDTO> getAllCategoriesCriteriaAPI() {}

    public List<CategoryDTO> getEachPossibleCategory() {
        var categories = categoryRepository.findEachPossibleCategory().stream()
                .map(row -> new CategoryDTO(
                        (Long) row[0],
                        (String) row[1]
                ))
                .toList();

        if (categories.isEmpty()) {
            throw new CategoryException("no categories found");
        }

        return categories;
    }

    public void createNewCategory(CreateNewCategoryRequestDTO requestDTO) {
        // set the description of the parent category to null
        if (requestDTO.getParentId() != null) {
            var parentCategory = categoryRepository.findById(requestDTO.getParentId())
                    .orElseThrow(() -> new CategoryException("parent category not found"));
                    // although no need to throw an exception

            parentCategory.setDescription(null);
        }

        // concatenate the new list of categories to the existing categories
        List<Category> categories = mapToCategoriesRecursive(requestDTO);
        /* TODO: developer-constraint: the description of the above categories is set to null except for the last one(the utmost child category)*/

        try {
            categoryRepository.saveAll(categories);
        }
        catch (ConstraintViolationException e) {
            throw new CategoryInvalidDescriptionException(e.getMessage(), e.getConstraintViolations());
        }
    }

    public List<CategoryNode> getAllCategoriesAsStringsArray() {
        List<CategoryDTO> allCategories = this.getAllCategories();

//        List<String> paths = allCategories.stream()
//                .map(CategoryDTO::getPath)
//                .flatMap(path -> Arrays.stream(path.split(" - ")))
//                .toList();
//
//        return paths.toArray(new String[0]);

        return CategoryHierarchyTree.buildCategoryHierarchyTree(allCategories);
    }


//    helper private methods:

}
