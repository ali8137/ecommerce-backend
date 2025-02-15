package com.ali.ecommerce.controller;

import com.ali.ecommerce.exception.CustomJsonException;
import com.ali.ecommerce.exception.ProductException;
import com.ali.ecommerce.model.Category;
import com.ali.ecommerce.model.Product;
import com.ali.ecommerce.service.ProductService;
import com.ali.ecommerce.util.ProductFieldNames;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


// - Using @CrossOrigin annotation on Controller level:
// @CrossOrigin
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService service;

//    @Autowired
//    public ProductController(ProductService service) {
//        this.service = service;
//    }


//  - Method level CORS configuration:
//    @CrossOrigin(origins = "http://localhost:5173")
//    public void method1() {}


    /* TODO: developer-constraint: a ProductException propagates from the service layer
        to the below method
        */
//    //    @CrossOrigin
////    @GetMapping("/products")
//    public ResponseEntity<Page<Product>> getProductsVersion1NotGood(
//            @RequestParam(required = false) String parentCategory,
//            //  parentCategory example: "men", "women", etc...
//            //  you can make the above parameter to be enum
//            @RequestParam(required = false) String childCategory,
//            //  category example: "tops", etc...
//            //  you can make the above parameter to be enum
//            @RequestParam(required = false) List<String> colors,
//            //  color example: ["blue", "red", etc...]
//            @RequestParam(required = false) List<String> sizes,
//            //  size example: ["S", "M", etc...]
////            @RequestParam(required = false) List<String> prices,
//            //  price example: [[0,100], [100,200], etc...]
//            @RequestParam(required = false) Double minPrice,
//            //  price example: 0
//            @RequestParam(required = false) Double maxPrice,
//            //  price example: 100
////            validate the above parameters
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int pageSize,
//            @RequestParam(defaultValue = "id") String sortBy
//    ) /*throws ProductException*/ {
////      - it is wrong to add the throws CategoryException in the above method, because this will
////        delegate the exception to the DispatcherServlet, and the GlobalExceptionHandler will not
////        be able to intercept it and handle it
////      - edit to the above note: actually adding the above throws ProductException is not wrong
//
//        var products = service.getProducts(parentCategory, childCategory,
//                colors, sizes, minPrice, maxPrice, page, pageSize, sortBy);
//
////        if (products.isEmpty()){
////            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
////        }
//
//        return new ResponseEntity<>(products, HttpStatus.OK);
//    }


    /* TODO: developer-constraint: a ProductException propagates from the service layer
        to the below method
        */
    //    @CrossOrigin
    @GetMapping("")
    public ResponseEntity<Page<Product>> getProducts(
            @RequestParam(required = false) Long categoryId,
            //  categories example: id of category men - clothing - tops
            @RequestParam(required = false) List<String> colors,
            //  color example: ["blue", "red", etc...]
            @RequestParam(required = false) List<String> sizes,
            //  size example: ["SMALL", "MEDIUM", etc...]
//            @RequestParam(required = false) List<String> prices,
            //  price example: [[0,100], [100,200], etc...]
            @RequestParam(required = false) Double minPrice,
            //  price example: 0
            @RequestParam(required = false) Double maxPrice,
            //  price example: 100
            /* TODO: validate the
                above parameters
                */
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy
    ) /*throws ProductException*/ {
//      - it is wrong to add the throws CategoryException in the above method, because this will
//        delegate the exception to the DispatcherServlet, and the GlobalExceptionHandler will not
//        be able to intercept it and handle it
//      - edit to the above note: actually adding the above throws ProductException is not wrong

//        log.info("controller layer:" +
//                        "categoryId: {}, " +
//                        "color: {}, " +
//                        "size: {}, " +
//                        "size class: {} " +
//                        "minPrice: {}, maxPrice: {}, " +
//                        "pageNumber: {}, pageSize: {}, " +
//                        "sortBy: {}", categoryId, colors,
//                sizes, sizes.getClass(), minPrice, maxPrice, page, pageSize, sortBy);

        var products = service.getProducts(categoryId,
                colors, sizes, minPrice, maxPrice, page, pageSize, sortBy);

//        if (products.isEmpty()){
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }

        return new ResponseEntity<>(products, HttpStatus.OK);
    }



    /* TODO: developer-constraint: a ProductException propagates from the service layer
        to the below method
        */
    //    @CrossOrigin
    @GetMapping("/products-criteria-api")
    public ResponseEntity<Page<Product>> getProductsCriteriaAPI(
            @RequestParam(required = false) List<String> categories,
            //  categories example: ["men", "clothing", "tops"]
            //  you can make the above parameter to be enum
            @RequestParam(required = false) List<String> colors,
            //  color example: ["blue", "red", etc...]
            @RequestParam(required = false) List<String> sizes,
            //  size example: ["S", "M", etc...]
            @RequestParam(required = false) String pricesData,
            //  price example: [[0,100], [100,200], etc...]
//            validate the above parameters
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy
    ) /*throws ProductException*/ {
//      - it is wrong to add the throws CategoryException in the above method, because this will
//        delegate the exception to the DispatcherServlet, and the GlobalExceptionHandler will not
//        be able to intercept it and handle it
//      - edit to the above note: actually adding the above throws ProductException is not wrong

        log.info("pricesData: {}", pricesData);

        var products = service.getProductsCriteriaAPi(categories,
                colors, sizes, pricesData, page, pageSize, sortBy);

//        if (products.isEmpty()){
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }

        return new ResponseEntity<>(products, HttpStatus.OK);
    }






    /* TODO: developer-constraint: a ProductException propagates from the service layer
        to the below method
        */
    //    @CrossOrigin
    @GetMapping("/products-criteria-apiV2")
    public ResponseEntity<Page<Product>> getProductsCriteriaAPI2(
            @RequestParam(required = false) Long categoryId,
            //  categories example: ["men", "clothing", "tops"]
            //  you can make the above parameter to be enum
            @RequestParam(required = false) List<String> colors,
            //  color example: ["blue", "red", etc...]
            @RequestParam(required = false) List<String> sizes,
            //  size example: ["S", "M", etc...]
            @RequestParam(required = false) String pricesData,
            //  price example: [[0,100], [100,200], etc...]
//            validate the above parameters
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "ASC") String sortDirection
    ) /*throws ProductException*/ {
//      - it is wrong to add the throws ProductException in the above method, because this will
//        delegate the exception to the DispatcherServlet, and the GlobalExceptionHandler will not
//        be able to intercept it and handle it
//      - edit to the above note: actually adding the above throws ProductException is not wrong

        log.info("pricesData: {}", pricesData);

//        try {
            var products = service.getProductsCriteriaAPi2(categoryId,
                    colors, sizes, pricesData, page, pageSize, sortBy, sortDirection);
//        }
//        catch(ProductException e) {
//            return new ResponseEntity<>(products, HttpStatus.BAD_REQUEST);
////          - "products" object instance can't be accessed in the catch block while
////            the "products" object instance is defined in the try block
//        }

//        if (products.isEmpty()){
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }

        return new ResponseEntity<>(products, HttpStatus.OK);
    }


    /* TODO: developer-constraint: a ProductException propagates from the service layer
        to the below method
        */
    //    @CrossOrigin
    @GetMapping("/product-attributes")
    public ResponseEntity<List<String>> getProductAttributes() /*throws ProductException*/ {
//      - it is wrong to add the throws ProductException in the above method, because this will
//        delegate the exception to the DispatcherServlet, and the GlobalExceptionHandler will not
//        be able to intercept it and handle it
//      - edit to the above note: actually adding the above throws ProductException is not wrong

//        try {
        var attributes = service.getProductAttributes();
//        }
//        catch(ProductException e) {
//            return new ResponseEntity<>(attributes, HttpStatus.BAD_REQUEST);
////          - "attributes" object instance can't be accessed in the catch block while
////            the "attributes" object instance is defined in the try block
//        }

//        if (attributes.isEmpty()){
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }

        return new ResponseEntity<>(attributes, HttpStatus.OK);
    }



    /* TODO: developer-constraint: a ProductException propagates from the service layer
        to the below method
        */
    //    @CrossOrigin
    @GetMapping("/product-attributes2")
    public ResponseEntity<List<String>> getProductAttributes2() {

        var attributes = ProductFieldNames.getProductClassFieldNames();

//        if (attributes.isEmpty()){
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }

        return new ResponseEntity<>(attributes, HttpStatus.OK);
    }



    /* TODO: developer-constraint: a ProductException propagates from the service layer
        to the below method
        */
    //    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) /*throws ProductException*/ {
//      - it is wrong to add the throws ProductException in the above method, because this will
//        delegate the exception to the DispatcherServlet, and the GlobalExceptionHandler will not
//        be able to intercept it and handle it
//      - edit to the above note: actually adding the above throws ProductException is not wrong

//        try {
        var product = service.getProductById(id);
//        }
//        catch(ProductException e) {
//            return new ResponseEntity<>(product, HttpStatus.BAD_REQUEST);
////          - "product" object instance can't be accessed in the catch block while
////            the "product" object instance is defined in the try block
//        }

//        if (product == null){
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }

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
//      - it is wrong to add the throws CategoryException in the above method, because this will
//        delegate the exception to the DispatcherServlet, and the GlobalExceptionHandler will not
//        be able to intercept it and handle it
//      - edit to the above note: actually adding the above throws ProductException is not wrong

        service.updateProduct(productId, updatedProduct);

        return new ResponseEntity<>("Product updated successfully", HttpStatus.OK);

    }


//    //    @CrossOrigin
//    @GetMapping("{}/{id}")
//    public ResponseEntity<Product> getProductById(@PathVariable("id") String id) {
//        return new ResponseEntity<>(service.getProductById(id), HttpStatus.OK);
//    }
//
//
//
//
//    //    @CrossOrigin
//    @ResponseStatus(
//            HttpStatus.NO_CONTENT
//            //   HttpStatus.OK
//            //   HttpStatus.CREATED
//    )
//    @GetMapping("/{id}")
//    public void /*or ResponseEntity<String>*/  getMethod1(
//            @RequestParam("paramName1") ClassName1 obj1,
//            @PathVariable("id") ClassName2 obj2
//    ) {
//
//        //    delegating the functionality to the corresponding Service method...
//
//        return new ResponseEntity<>(serviceObj1.serviceMethod1() /* or any other class object instance such as String, ... */ , HttpStatus.OK);
//        //    - or
//        //      return ResponseEntity.ok("company updated successfully");
//
//    }
//
//
//    //    @CrossOrigin
//    @ResponseStatus(
//            HttpStatus.NO_CONTENT
//            //    HttpStatus.OK
//            //    HttpStatus.CREATED
//    )
//    @PostMapping()
//    public void /*or ResponseEntity<String>*/  postMethod1(@RequestBody ClassName1 obj1) {
//
//        //    delegating the functionality to the corresponding Service method...
//
//        return new ResponseEntity<>(serviceObj1.serviceMethod2() /* or any other class object instance such as String, ... */ , HttpStatus.OK);
//        //    - or
//        //      return ResponseEntity.ok("company updated successfully");
//
//    }
//
//
//    //    @CrossOrigin
//    @ResponseStatus(
//            HttpStatus.NO_CONTENT
//            //    HttpStatus.OK
//            //    HttpStatus.CREATED
//    )
//    @PutMapping("/{id}")
//    public void /*or ResponseEntity<String>*/ putMethod1(
//            @RequestBody ClassName1 obj1,
//            @PathVariable("id") ClassName2 obj2
//    ) {
//
//        //    delegating the functionality to the corresponding Service method...
//
//        return new ResponseEntity<>(serviceObj1.serviceMethod3() /* or any other class object instance such as String, ... */ , HttpStatus.OK);
//        //    - or
//        //      return ResponseEntity.ok("company updated successfully");
//
//    }
//
//
//
//    //    @CrossOrigin
//    @ResponseStatus(
//            HttpStatus.NO_CONTENT
//            //    HttpStatus.OK
//            //    HttpStatus.CREATED
//    )
//    @DeleteMapping("/{id}")
//    public void /*or ResponseEntity<String>*/ putMethod1(
//            @PathVariable("id") ClassName1 obj1
//    ) {
//
//        //    delegating the functionality to the corresponding Service method...
//
//        return new ResponseEntity<>(serviceObj1.serviceMethod4() /* or any other class object instance such as String, ... */ , HttpStatus.OK);
//        //    - or
//        //      return ResponseEntity.ok("company updated successfully");
//
//    }


}

