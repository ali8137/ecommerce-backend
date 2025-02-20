package com.ali.ecommerce.repository;

import com.ali.ecommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

//    name-based queries:

    Page<Product> findAllByName(String name, Pageable pageable);

//    JPQL queries:

//    native SQL queries:

    @Query(
            value = "SELECT DISTINCT p.* " +
                    "FROM product p " +
                    "JOIN product_qty_per_size_and_colors pq " +
                    "ON p.id = pq.product_id " +
                    "WHERE (" +
                                "CASE " +
                                "WHEN :categoryId IS NULL " +
                                "THEN TRUE " +
                                "ELSE FALSE " +
                                "END " +
                                // CASE expression is used for better performance
                                "OR p.category_id = :categoryId" +
                            ") " +
                            "AND (" +
                            "pq.product_size IN (:sizes)" +
                            ") " +
                            "AND (" +
                            "pq.product_color IN (:colors))" +
                            "AND (CASE " +
                                "WHEN :minPrice IS NULL " +
                                "THEN TRUE " +
                                "ELSE FALSE " +
                                "END " +
                                "OR (p.price >= :minPrice AND p.price <= :maxPrice)" +
                            ")",
            nativeQuery = true
    )
    Page<Product> findProductsFilteredAndSorted(@Param("categoryId") Long categoryId,
                                                @Param("colors") List<String> colors,
                                                @Param("sizes") List<String> sizes,
                                                @Param("minPrice") Double minPrice,
                                                @Param("maxPrice") Double maxPrice,
                                                Pageable pageable
    );
    /* TODO: developer-constraint: the above method won't work properly when the parameter "sizes" or "colors" is null or empty*/

    @Query(
            value = "SELECT COLUMN_NAME " +
                    "FROM INFORMATION_SCHEMA.COLUMNS " +
                    "WHERE TABLE_NAME = 'product'",
            nativeQuery = true
    )
    List<String> getProductTableAttributes();
}
