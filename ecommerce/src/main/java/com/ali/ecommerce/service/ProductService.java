package com.ali.ecommerce.service;


import com.ali.ecommerce.exception.CategoryException;
import com.ali.ecommerce.exception.CustomJsonException;
import com.ali.ecommerce.exception.ProductException;
import com.ali.ecommerce.model.Product;
import com.ali.ecommerce.model.Rating;
import com.ali.ecommerce.model.Review;
import com.ali.ecommerce.repository.CategoryRepository;
import com.ali.ecommerce.repository.ProductRepository;
import com.ali.ecommerce.repository.service.ProductSpecification;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
//    private final CategoryRepository categoryRepository;

//    @Autowired
//    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
//        this.productRepository = productRepository;
//        this.categoryRepository = categoryRepository;
//    }

//    public Product getAllProducts() {
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
//        return repository.findAll().orElseThrow(() -> new ProductException("no Products"));
//    }


//    public Page<Product> getProductsVersion1NotGood(
//            String parentCategory,
//            String childCategory,
//            List<String> colors,
//            List<String> sizes,
////            List<String> prices,
//            Double minPrice,
//            Double maxPrice,
//            int pageNumber, int pageSize,
//            String sortBy
//    ) {
//        log.info("parentCategory: {}, childCategory: {}, " +
//                "color: {}, " +
//                "size: {}, " +
//                "minPrice: {}, maxprice: {}, " +
//                "pageNumber: {}, pageSize: {}, " +
//                "sortBy: {}", parentCategory, childCategory, colors,
//                sizes, minPrice, maxPrice, pageNumber, pageSize, sortBy);
//
//        Pageable pageable =
//                PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
//
//        var filteredAndSortedProducts = productRepository.findProductsFilteredAndSorted(parentCategory, childCategory,
//                colors, sizes, minPrice, maxPrice, pageable);
//
//        if (filteredAndSortedProducts.isEmpty()) {
//            throw new ProductException("No products found");
//        }
//
//        return filteredAndSortedProducts;
//    }

    public Page<Product> getProducts(
            Long categoryId,
            List<String> colors,
            List<String> sizes,
//            List<String> prices,
            Double minPrice, Double maxPrice,
            int pageNumber, int pageSize,
            /* TODO: for the above parameter "pageNumber", handle the case (exception) of
                the user entering a out of range number
                */
            String sortBy
    ) {
        log.info("service layer: categoryId: {}, color: {}, size: {}, size class: {} " +
                        "minPrice: {}, maxPrice: {}, " +
                        "pageNumber: {}, pageSize: {}, " +
                        "sortBy: {}", categoryId, colors, sizes, sizes.getClass(), minPrice, maxPrice,
                pageNumber, pageSize, sortBy);

        Pageable pageable =
                PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));

        var filteredAndSortedProducts = productRepository.findProductsFilteredAndSorted(
                categoryId, colors, sizes, minPrice, maxPrice, pageable);

        if (filteredAndSortedProducts.isEmpty()) {
            throw new ProductException("No products found");
        }

        return filteredAndSortedProducts;
    }


    public Page<Product> getProductsCriteriaAPi(
            List<String> categories,
            List<String> colors,
            List<String> sizes,
            String pricesData,
            int pageNumber, int pageSize,
            String sortBy
    ) {

        log.info("pricesData: {}", pricesData);

//      - the below mapping is needed when having an incoming JSON string that is to be mapped to
//        a different class from String, Lis<String>, or numeric classes like
//        Integer, Double, int, double, etc. or when The incoming parameter is already in
//        the required Java type and format.
        List<double[]> priceRanges = new ArrayList<>();

        if (pricesData != null) {
            ObjectMapper objectMapper = new ObjectMapper();

            try {
                //  decode the JSON string into a List of double[]
                priceRanges /*parsedPricesData*/ = objectMapper.
                        readValue(pricesData, new TypeReference<List<double[]>>() {});
                log.info("priceRanges: {}", priceRanges);
            } catch (JsonProcessingException e) {
                throw new CustomJsonException(e.getMessage());
            }
        }


        Specification<Product> spec = ProductSpecification.filterProducts(
                categories, colors, sizes, pricesData != null ? priceRanges : null
        );

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));

        Page<Product> products = productRepository.findAll(spec, pageable);

        if (products.isEmpty()) {
            throw new ProductException("No products found");
        }

        return products;
    }


    public Page<Product> getProductsCriteriaAPi2(
            Long categoryId,
            List<String> colors,
            List<String> sizes,
            String pricesData,
            int pageNumber, int pageSize,
            String sortBy,
            String sortDirection
    ) {

        log.info("pricesData: {}", pricesData);

//      - the below mapping is needed when having an incoming JSON string that is to be mapped to
//        a different class from String, Lis<String>, or numeric classes like
//        Integer, Double, int, double, etc. or when The incoming parameter is already in
//        the required Java type and format.
        List<double[]> priceRanges = new ArrayList<>();

        Sort.Direction direction = Sort.Direction.fromString(sortDirection);

        if (pricesData != null) {
            ObjectMapper objectMapper = new ObjectMapper();

            try {
                //  decode the JSON string into a List of double[]
                priceRanges /*parsedPricesData*/ = objectMapper.
                        readValue(pricesData, new TypeReference<List<double[]>>() {});
                log.info("priceRanges: {}", priceRanges);
            } catch (JsonProcessingException e) {
                throw new CustomJsonException(e.getMessage());
            }
        }


        Specification<Product> spec = ProductSpecification.filterProducts2(
                categoryId, colors, sizes, pricesData != null ? priceRanges : null
        );

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(direction, sortBy));

        Page<Product> products = productRepository.findAll(spec, pageable);

        if (products.isEmpty()) {
            throw new ProductException("No products found");
        }
//      - based on the business logic in the frontend, I think throwing an exception
//        above is not strictly needed

        return products;
    }

    public List<String> getProductAttributes() {
        var attributes = productRepository.getProductTableAttributes();

        if (attributes.isEmpty()) {
            throw new ProductException("No attributes found");
        }

        return attributes;
    }

    public Product getProductById(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException("Product not found"));
        
        return product;
    }


    public void addProducts(List<Product> products) {

        for (Product product : products) {
            setUpProduct(product);
        }

        productRepository.saveAll(products);
    }

    public void addProduct(Product product) {

        setUpProduct(product);

        productRepository.save(product);
    }

    public void updateProduct(Long productId, Product updatedProduct) {
        Optional<Product> productOptional = productRepository.findById(productId);

        if (productOptional.isEmpty()) {
            throw new ProductException("Product not found");
        }

//        if (productOptional.isPresent()) {
            Product product = productOptional.get();

            product.setName(updatedProduct.getName());
            product.setDescription(updatedProduct.getDescription());
            product.setPrice(updatedProduct.getPrice());
            product.setQtyPerSizeAndColors(updatedProduct.getQtyPerSizeAndColors());
            product.setImages(updatedProduct.getImages());
//            the above properties are not relationship data fields in the Product class


            product.setCategory(updatedProduct.getCategory());
//            the above property was set because the Category data field in the Product class is annotated with @JoinColumn

            for (Review review : updatedProduct.getReviews()) {
                product.addReview(review);
            }
            for (Rating productRating : updatedProduct.getProductRatings()) {
                product.addRating(productRating);
            }
//            the above two methods (addReview and addRating) include setter methods that
//            will set the Product data field of the Review and Rating classes to point to
//            the related Product entity; because Product data field in these classes is
//            annotated with @JoinColumn
            product.setReviews(updatedProduct.getReviews());
            product.setProductRatings(updatedProduct.getProductRatings());
//          - the above two properties were set because they are relationship data fields
//            in the Product class, setting them is just to update Java object graph
//            of the session, and setting them has nothing to do with EntityManager
//            operations (persist, merge, remove, refresh, detach)

            productRepository.save(product);
//        }
    }

//    helper private methods:

    private void setUpProduct(Product product) {
//        if (product.getReviews() != null) {
//            for (Review review : product.getReviews()) {
//                review.setProduct(product);
//            }
//        }
//        if (product.getProductRatings() != null) {
//            for (Rating productRating : product.getProductRatings()) {
//                productRating.setProduct(product);
//            }
//        }
////        the above two if statements are not necessary, because when
////        creating a new product, it still has no reviews or ratings yet. so due to business logic

//        if (product.getCategory() != null) {
//
//            var category = categoryRepository.findById(product.getCategory().getId())
//                    .orElseThrow(() -> new CategoryException("Category not found"));
//
//            product.setCategory(category);
//        }
////      - the above is not necessary; because the JSON request of the new
////        product to be added will be deserialized into a Product entity with
////        the Category data field being set based on the value of the key
////        property in the JSON object being sent. and spring data JPA needs
////        only the id of the category data field/entity in order to associate
////        the product entity with it.
////      - but it is recommended to add the above
////        code to make sure firstly the category with the sent id is already present
////        in the database to avoid violating the foreign key constraint.
////      - update: the above is not necessary at all. in case there was any violation, an exception
////        will be thrown. and hence we can catch it and handle it.
    }

//    private void insertIntoTempTableCategoryNames(List<String> categoryNamesList) {
//
//        Collections.reverse(categoryNamesList);
//
//        for (String categoryName : categoryNamesList) {
//            repository.insertIntoTempTableCategoryNames(categoryName);
//        }
//    }
}
