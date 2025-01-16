package com.ali.ecommerce.controller;

import com.ali.ecommerce.DTO.databaseDTO.CategoryDTO;
import com.ali.ecommerce.DTO.CreateNewCategoryRequestDTO;
import com.ali.ecommerce.exception.CategoryException;
import com.ali.ecommerce.exception.CategoryInvalidDescriptionException;
import com.ali.ecommerce.model.Category;
import com.ali.ecommerce.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


// - Using @CrossOrigin annotation on Controller level:
// @CrossOrigin
// @CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CategoryController {


    private final CategoryService categoryService;

//    @Autowired
//    public CategoryController(CategoryService categoryService) {
//        this.categoryService = categoryService;
//    }


//  - Method level CORS configuration:
//    @CrossOrigin(origins = "http://localhost:5173")
//    public void method1() {}


    /* developer-constraint: a CategoryException propagates from the service layer
    to the below method.
    */
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>>  getAllCategories() throws CategoryException {
//      - it is wrong to add the throws CategoryException in the above method, because this will
//        delegate the exception to the DispatcherServlet, and the GlobalExceptionHandler will not
//        be able to intercept it and handle it.
//      - edit to the above note: actually adding the above throws CategoryException is not wrong

        var categories = categoryService.getAllCategories();

//        if (categories.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }

        return new ResponseEntity<>(categories, HttpStatus.OK);

    }


    /* developer-constraint: a CategoryException propagates from the service layer
    to the below method
    */
    @GetMapping("/possible-categories")
    public ResponseEntity<List<CategoryDTO>>  getEachPossibleCategory() /*throws CategoryException*/ {
//      - it is wrong to add the throws CategoryException in the above method, because this will
//        delegate the exception to the DispatcherServlet, and the GlobalExceptionHandler will not
//        be able to intercept it and handle it
//      - edit to the above note: actually adding the above throws CategoryException is not wrong

        var categories = categoryService.getEachPossibleCategory();

//        if (categories.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }

        return new ResponseEntity<>(categories, HttpStatus.OK);

    }



    /* developer-constraint: a CategoryInvalidDescriptionException propagates from the service layer
    to the below method
    */
//  - in the frontend, the user first retrieves the present
//    categories with their id, and then he chooses one of them as parent category
//    of the new category(ies) he wants to add.
    @PostMapping("/add-category")
    public ResponseEntity<String>  createNewCategory(
        @Valid @RequestBody CreateNewCategoryRequestDTO requestDTO
    ) /*throws CategoryInvalidDescriptionException*/ {
//      - it is wrong to add the throws CategoryInvalidDescriptionException in the above method, because this will
//        delegate the exception to the DispatcherServlet, and the GlobalExceptionHandler will not
//        be able to intercept it and handle it
//      - edit to the above note: actually adding the above throws CategoryInvalidDescriptionException is not wrong

        categoryService.createNewCategory(requestDTO);

        return new ResponseEntity<>("company created successfully", HttpStatus.OK);

    }




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
