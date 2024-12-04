package com.ali.ecommerce.controller;

import com.ali.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


// - Using @CrossOrigin annotation on Controller level:
// @CrossOrigin
// @CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

//    @Autowired
//    public ProductController(ProductService service) {
//        this.service = service;
//    }


//  - Method level CORS configuration:
//    @CrossOrigin(origins = "http://localhost:5173")
//    public void method1() {}

    //    @CrossOrigin
    @GetMapping
    public ResponseEntity<String> getAllProducts() {
        return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
    }



    //    @CrossOrigin
    @ResponseStatus(
            HttpStatus.NO_CONTENT
            //   HttpStatus.OK
            //   HttpStatus.CREATED
    )
    @GetMapping("/{id}")
    public void /*or ResponseEntity<String>*/  getMethod1(
            @RequestParam("paramName1") ClassName1 obj1,
            @PathVariable("id") ClassName2 obj2
    ) {

        //    delegating the functionality to the corresponding Service method...

        return new ResponseEntity<>(serviceObj1.serviceMethod1() /* or any other class object instance such as String, ... */ , HttpStatus.OK);
        //    - or
        //      return ResponseEntity.ok("company updated successfully");

    }


    //    @CrossOrigin
    @ResponseStatus(
            HttpStatus.NO_CONTENT
            //    HttpStatus.OK
            //    HttpStatus.CREATED
    )
    @PostMapping()
    public void /*or ResponseEntity<String>*/  postMethod1(@RequestBody ClassName1 obj1) {

        //    delegating the functionality to the corresponding Service method...

        return new ResponseEntity<>(serviceObj1.serviceMethod2() /* or any other class object instance such as String, ... */ , HttpStatus.OK);
        //    - or
        //      return ResponseEntity.ok("company updated successfully");

    }


    //    @CrossOrigin
    @ResponseStatus(
            HttpStatus.NO_CONTENT
            //    HttpStatus.OK
            //    HttpStatus.CREATED
    )
    @PutMapping("/{id}")
    public void /*or ResponseEntity<String>*/ putMethod1(
            @RequestBody ClassName1 obj1,
            @PathVariable("id") ClassName2 obj2
    ) {

        //    delegating the functionality to the corresponding Service method...

        return new ResponseEntity<>(serviceObj1.serviceMethod3() /* or any other class object instance such as String, ... */ , HttpStatus.OK);
        //    - or
        //      return ResponseEntity.ok("company updated successfully");

    }



    //    @CrossOrigin
    @ResponseStatus(
            HttpStatus.NO_CONTENT
            //    HttpStatus.OK
            //    HttpStatus.CREATED
    )
    @DeleteMapping("/{id}")
    public void /*or ResponseEntity<String>*/ putMethod1(
            @PathVariable("id") ClassName1 obj1
    ) {

        //    delegating the functionality to the corresponding Service method...

        return new ResponseEntity<>(serviceObj1.serviceMethod4() /* or any other class object instance such as String, ... */ , HttpStatus.OK);
        //    - or
        //      return ResponseEntity.ok("company updated successfully");

    }


}

