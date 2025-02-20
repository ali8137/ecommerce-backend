package com.ali.ecommerce.service;


import com.ali.ecommerce.exception.CustomJsonException;
import com.ali.ecommerce.exception.ProductException;
import com.ali.ecommerce.model.Product;
import com.ali.ecommerce.model.Rating;
import com.ali.ecommerce.model.Review;
import com.ali.ecommerce.repository.ProductRepository;
import com.ali.ecommerce.repository.service.ProductSpecification;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
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
public class ProductService {

    private final ProductRepository productRepository;

    public Page<Product> getProducts(
            Long categoryId,
            List<String> colors,
            List<String> sizes,
            Double minPrice, Double maxPrice,
            int pageNumber, int pageSize,
            /* TODO: for the above parameter "pageNumber", handle the case (exception) of the user entering an out-of-range number*/
            String sortBy
    ) {
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
        List<double[]> priceRanges = new ArrayList<>();

        if (pricesData != null) {
            ObjectMapper objectMapper = new ObjectMapper();

            try {
                //  decode the JSON string into a List of double[]
                priceRanges = objectMapper.
                        readValue(pricesData, new TypeReference<List<double[]>>() {});
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
        List<double[]> priceRanges = new ArrayList<>();

        Sort.Direction direction = Sort.Direction.fromString(sortDirection);

        if (pricesData != null) {
            ObjectMapper objectMapper = new ObjectMapper();

            try {
                //  decode the JSON string into a List of double[]:
                priceRanges = objectMapper.
                        readValue(pricesData, new TypeReference<List<double[]>>() {});
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
        // based on the business logic in the frontend, I think throwing an exception above is not strictly needed

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

        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductException("Product not found"));
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

        Product product = productOptional.get();

        product.setName(updatedProduct.getName());
        product.setDescription(updatedProduct.getDescription());
        product.setPrice(updatedProduct.getPrice());
        product.setQtyPerSizeAndColors(updatedProduct.getQtyPerSizeAndColors());
        product.setImages(updatedProduct.getImages());

        product.setCategory(updatedProduct.getCategory());

        for (Review review : updatedProduct.getReviews()) {
            product.addReview(review);
        }
        for (Rating productRating : updatedProduct.getProductRatings()) {
            product.addRating(productRating);
        }
        product.setReviews(updatedProduct.getReviews());
        product.setProductRatings(updatedProduct.getProductRatings());

        productRepository.save(product);
    }

//    helper private methods:

    private void setUpProduct(Product product) {}
}
