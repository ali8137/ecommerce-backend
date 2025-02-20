package com.ali.ecommerce.controller;

import com.ali.ecommerce.model.Product;
import com.ali.ecommerce.service.ProductService;
import com.ali.ecommerce.util.ProductFieldNames;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    /* TODO: developer-constraint: a ProductException propagates from the service layer
        to the below method
        */
    @GetMapping("")
    public ResponseEntity<Page<Product>> getProducts(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) List<String> colors, // ["blue", "red", ...]
            @RequestParam(required = false) List<String> sizes, // ["SMALL", "MEDIUM", ...]
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            /* TODO: validate the above parameters*/
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy
    ) /*throws ProductException*/ {
        var products = service.getProducts(categoryId,
                colors, sizes, minPrice, maxPrice, page, pageSize, sortBy);

        return new ResponseEntity<>(products, HttpStatus.OK);
    }


    /* TODO: developer-constraint: a ProductException propagates from the service layer
        to the below method
        */
    @GetMapping("/products-criteria-api")
    public ResponseEntity<Page<Product>> getProductsCriteriaAPI(
            @RequestParam(required = false) List<String> categories, // ["men", "clothing", "tops"]
            @RequestParam(required = false) List<String> colors, // ["blue", "red", ...]
            @RequestParam(required = false) List<String> sizes, // ["SMALL", "MEDIUM", ...]
            @RequestParam(required = false) String pricesData, // [[0,100], [100,200], ...]
            /* TODO: validate the above parameters*/
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy
    ) /*throws ProductException*/ {
        var products = service.getProductsCriteriaAPi(categories,
                colors, sizes, pricesData, page, pageSize, sortBy);

        return new ResponseEntity<>(products, HttpStatus.OK);
    }


    /* TODO: developer-constraint: a ProductException propagates from the service layer
        to the below method
        */
    @GetMapping("/products-criteria-apiV2")
    public ResponseEntity<Page<Product>> getProductsCriteriaAPI2(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) List<String> colors, // ["blue", "red", ...]
            @RequestParam(required = false) List<String> sizes, // ["SMALL", "MEDIUM", ...]
            @RequestParam(required = false) String pricesData, // [[0,100], [100,200], ...]
            /* TODO: validate the above parameters*/
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "ASC") String sortDirection
    ) /*throws ProductException*/ {
        var products = service.getProductsCriteriaAPi2(categoryId,
                colors, sizes, pricesData, page, pageSize, sortBy, sortDirection);

        return new ResponseEntity<>(products, HttpStatus.OK);
    }


    /* TODO: developer-constraint: a ProductException propagates from the service layer
        to the below method
        */
    @GetMapping("/product-attributes")
    public ResponseEntity<List<String>> getProductAttributes() /*throws ProductException*/ {
        var attributes = service.getProductAttributes();

        return new ResponseEntity<>(attributes, HttpStatus.OK);
    }


    /* TODO: developer-constraint: a ProductException propagates from the service layer
        to the below method
        */
    @GetMapping("/product-attributes2")
    public ResponseEntity<List<String>> getProductAttributes2() {
        var attributes = ProductFieldNames.getProductClassFieldNames();

        return new ResponseEntity<>(attributes, HttpStatus.OK);
    }


    /* TODO: developer-constraint: a ProductException propagates from the service layer
        to the below method
        */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) /*throws ProductException*/ {
        var product = service.getProductById(id);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }


    /* TODO: for testing: case 1: adding products with already present categories.
        case 2: adding products with new categories
        */
    @PostMapping("/add-products")
    public ResponseEntity<String> addProducts(@RequestBody List<Product> products) {
        service.addProducts(products);

        return new ResponseEntity<>("Products added successfully" , HttpStatus.CREATED);
    }


    /* TODO: for testing: case 1: adding products with already present categories.
        case 2: adding products with new categories
        */
    @PostMapping("/add-product")
    public ResponseEntity<String> addProduct(@RequestBody Product product) {
        service.addProduct(product);

        return new ResponseEntity<>("Product added successfully" , HttpStatus.CREATED);
    }


    /* TODO: developer-constraint: a CategoryException propagates from the service layer
        to the below method
        */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(
            @PathVariable("id") Long productId,
            @RequestBody Product updatedProduct
    ) /*throws ProductException*/ {
        service.updateProduct(productId, updatedProduct);

        return new ResponseEntity<>("Product updated successfully", HttpStatus.OK);
    }
}

