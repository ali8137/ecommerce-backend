package com.ali.ecommerce.repository;

import com.ali.ecommerce.model.Category;
import com.ali.ecommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
//    JpaSpecificationExecutor<> was added above because we are using Specification
//    to implement criteria API for certain method(s) in this repository


//    method-name-based queries:

    Page<Product> findAllByName(String name, Pageable pageable);
    //    wrapping Page inside Optional is unnecessary, because page can never be null
    //    the first parameter should be written above; otherwise running of the application will fail


//    JPQL queries:

//    native SQL queries:


//    @Query(
//            value = //     recursive query:
//                    "WITH RECURSIVE categoryHierarchy AS (" +
//                    "SELECT id, name " +
//                    "FROM category c " +
//                    "WHERE :parentCategory IS NULL OR (" +
//                            "c.parent_category_id IS NULL " +
//                            "AND c.name = :parentCategory" +
//                            ") " +
//
//                    "UNION " +
//
//                    "SELECT id, name " +
//                    "FROM category c " +
//                    "INNER JOIN categoryHierarchy ch " +
//                    "ON :childCategory IS NULL " +
//                            "OR c.category.childCategory = ch.category.parentCategory " +
//                    ")" +
//                    //  - the above will retrieve the category rows that
//                    //    have the farthest child category with name as "childCategory"
//
//                    //     retrieving the data from the view "categoryHierarchy":
//                    "SELECT p.* " +
//                    "FROM categoryHierarchy ch " +
//                    "INNER JOIN product p " +
//                    "ON :childCategory IS NULL OR (" +
//                            "ch.name = :parentCategory " +
//                            "AND ch.id = p.category" +
//                            ") " +
//                    "WHERE :minPrice IS NULL OR (" +
//                            "p.price >= :minPrice AND p.price <= :maxPrice" +
//                            ") " +
//                            "AND (" +
//                            ":size IS NULL OR p.size IN (:sizes)" +
//                            ") " +
//                            "AND (" +
//                            ":color IS NULL OR p.color IN (:colors)" +
//                            ") ",
//                    //  - the above will retrieve the id of the rows with name as "parentCategory"
//                    //    and with the farthest child category with name as "childCategory"
//            nativeQuery = true
//    )
//    Page<Product> findProductsFilteredAndSortedVersion1NotGood(@Param("parentCategory") String parentCategory,
//                                 String childCategory,
//                                 @Param("sizes") List<String> sizes,
//                                 @Param("colors") List<String> colors,
//                                 @Param("minPrice") Double minPrice,
//                                 @Param("maxPrice") Double maxPrice,
//                                 Pageable pageable
//    );
//    //    wrapping Page inside Optional is unnecessary, because page can never be null


    @Query(
            value = //     retrieving the data from the view "categoryHierarchy":
                    "SELECT DISTINCT p.* " +
                    "FROM product p " +
                    "JOIN product_qty_per_size_and_colors pq " +
                    "ON p.id = pq.product_id " +
                    "WHERE (" +
                                "CASE " +
                                "WHEN :categoryId IS NULL " +
                                "THEN TRUE " +
                                "ELSE FALSE " +
                                "END " +
                                // - CASE expression is used for better performance, because the
                                //   condition logic is always constant across the different rows of this query
                                "OR p.category_id = :categoryId" +
                            ") " +
                            "AND (" +
//                                "CASE " +
//                                "WHEN :sizes IS NULL " +
//                                // - the above line is SQL-syntactically wrong because ":arrayVariable1" can't
//                                //   be written on the left side of a conditional logic. like ":arrayVariable1 IS NULL",
//                                //   ":arrayVariable1 IN", ":arrayVariable1 = ...", "(:arrayVariable1) IS NULL",
//                                //   "(:arrayVariable1) IN", "(:arrayVariable1) = ...", etc.
//                                "THEN TRUE " +
//                                "ELSE FALSE " +
//                                "END " +
//                                "OR " +
                            "pq.product_size IN (:sizes)" +
                            ") " +
                            "AND (" +
//                                "CASE " +
//                                "WHEN :colors IS NULL " +
//                                // - the above line is SQL-syntactically wrong because ":arrayVariable1" can't
//                                //   be written on the left side of a conditional logic. like ":arrayVariable1 IS NULL",
//                                //   ":arrayVariable1 IN", ":arrayVariable1 = ...", "(:arrayVariable1) IS NULL",
//                                //   "(:arrayVariable1) IN", "(:arrayVariable1) = ...", etc.
//                                "THEN TRUE " +
//                                "ELSE FALSE " +
//                                "END " +
//                                "OR " +
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
    //    wrapping Page inside Optional is unnecessary, because page can never be null
    /* important: the above method won't work properly when the parameter "sizes" or
    "colors" is null or empty
    */


//    Page<Product> findAll(Specification<Product> spec, Pageable pageable);


    @Query(
            value = "SELECT COLUMN_NAME " +
                    "FROM INFORMATION_SCHEMA.COLUMNS " +
                    "WHERE TABLE_NAME = 'product'",
            nativeQuery = true
    )
    List<String> getProductTableAttributes();





//  - stored procedures, functions, triggers, views, ... must be created in the database and not here.
//  - in case you need to execute a complex SQL query that results in a table which is not mapped to
//    any entity class, then don't use spring data JPA and use instead either EntityManager or JdbcTemplate.
//    also, when the query is applied on a table that is not an entity class like a temporary table, ... then it is
//    better to either use EntityManager or JdbcTemplate

}
