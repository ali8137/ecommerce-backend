package com.ali.ecommerce.controller;

import com.ali.ecommerce.DTO.databaseDTO.CategoryDTO;
import com.ali.ecommerce.DTO.CreateNewCategoryRequestDTO;
import com.ali.ecommerce.exception.CategoryException;
import com.ali.ecommerce.service.CategoryService;
import com.ali.ecommerce.util.CategoryNode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
/* TODO: the above better be /api/categories with changing the endpoints below accordingly*/
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    /* TODO: developer-constraint: a CategoryException propagates from the service layer
        to the below method.
        */
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>>  getAllCategories() throws CategoryException {
        var categories = categoryService.getAllCategories();

        return new ResponseEntity<>(categories, HttpStatus.OK);
    }


    /* TODO: developer-constraint: a CategoryException propagates from the service layer
        to the below method
        */
    @GetMapping("/possible-categories")
    public ResponseEntity<List<CategoryDTO>>  getEachPossibleCategory() /*throws CategoryException*/ {
        var categories = categoryService.getEachPossibleCategory();

        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    /* TODO: developer-constraint: a CategoryException propagates from the service layer
        to the below method.
        */
    @GetMapping("/categories/hierarchy")
    public ResponseEntity<List<CategoryNode>>  getAllCategoriesAsStringsArray() throws CategoryException {
        List<CategoryNode> categoriesHierarchy = categoryService.getAllCategoriesAsStringsArray();

        return new ResponseEntity<>(categoriesHierarchy, HttpStatus.OK);
    }



    /* TODO: developer-constraint: a CategoryInvalidDescriptionException propagates from the service layer
        to the below method
        */
    @PostMapping("/add-category")
    public ResponseEntity<String>  createNewCategory(
        @Valid @RequestBody CreateNewCategoryRequestDTO requestDTO
    ) /*throws CategoryInvalidDescriptionException*/ {
        categoryService.createNewCategory(requestDTO);

        return new ResponseEntity<>("company created successfully", HttpStatus.OK);
    }
}
