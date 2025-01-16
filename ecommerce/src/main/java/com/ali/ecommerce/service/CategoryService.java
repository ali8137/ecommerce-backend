package com.ali.ecommerce.service;


import com.ali.ecommerce.DTO.databaseDTO.CategoryDTO;
import com.ali.ecommerce.DTO.CreateNewCategoryRequestDTO;
import com.ali.ecommerce.exception.CategoryException;
import com.ali.ecommerce.exception.CategoryInvalidDescriptionException;
import com.ali.ecommerce.model.Category;
import com.ali.ecommerce.repository.CategoryRepository;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

//import static com.ali.ecommerce.mapper.CategoryMapper.mapToCategories;
import static com.ali.ecommerce.mapper.CategoryMapper.mapToCategoriesRecursive;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;

//    @Autowired
//    public CategoryService(CategoryRepository categoryRepository) {
//        this.categoryRepository = categoryRepository;
//    }

//    public ClassName1 method1(ParameterClass1 obj1) {
//
//        //    business logic
//        //    database operations
//        //    file operations
//        //    network operations
//        //    data validation
//        //    data transformation
//        //    DTO-to-class conversion
//        //    class-to-DTO conversion
//        //    event-driven handling
//        //    email notification sending
//        //    caching
//        //    security-related operations (like JWT token generation, password encryption, etc.)
//        //    AI integration
//        //    exception handling
//        //    logging
//
//    }


    public List<CategoryDTO> getAllCategories() {
        var categories = categoryRepository.findAllCategoryHierarchies().stream()
                .map(row ->
                {
                    log.info("id: {}, id class: {}, path: {}, path class: {}",
                            row[0], row[0].getClass(), row[1], row[1].getClass());

                        return new CategoryDTO(
                        (Long) row[0],
//                        row[0] is an Object of value equal to the id of the category.
//                        the above is down casting to Long. this down-casting succeeds because the
//                        returned id by the database is of type "Long"
                        (String) row[1]
//                        row[1] is an Object of value equal to the name of the category.

                );
                }
                    )
                .toList();

        if (categories.isEmpty()) {
            throw new CategoryException("no categories found");
        }

        return categories;
    }


    /* TODO: develop the below method
        using Criteria API instead of @Query
        */
//    public List<CategoryDTO> getAllCategoriesCriteriaAPI() {
//
//    }

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

//        set the description of the parent category to null
        if (requestDTO.getParentId() != null) {
            var parentCategory = categoryRepository.findById(requestDTO.getParentId())
                    .orElseThrow(() -> new CategoryException("parent category not found"));
    //        no need to throw an exception

            parentCategory.setDescription(null);
        }
//      - even though we haven't manually/explicitly merged the above fetched entity that we updated in the
//        active persistence context here using save(), but at the end of this method, this transaction
//        will be also committed, and with that all the unsaved entities in this persistence like the above entity will be
//        flushed automatically into the database

//        parentCategoryOptional.ifPresent(parentCategory -> parentCategory.setDescription(null));

//        concatenate the new list of categories to the existing categories
        List<Category> categories = mapToCategoriesRecursive(requestDTO);
        /* developer-constraint the description of the above categories is set to null except for the
        last one (the most child category)
        * */

        log.info("categories: {}", categories);

        try {
            categoryRepository.saveAll(categories);
        }
        catch (ConstraintViolationException e) {
            throw new CategoryInvalidDescriptionException(e.getMessage(), e.getConstraintViolations());
        }
    }


//    helper private methods:

}
