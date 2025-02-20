package com.ali.ecommerce.repository;

import com.ali.ecommerce.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

//    methods with functionalities based on its name structure:

    boolean existsByName(String categoryName);

//    JPQL queries:


//    native SQL queries: methods with functionalities based on the SQL query specified in the annotation:

    /*
    result example:
    men - clothing - tops
    women - clothing - tops
    ...
    */
    @Query(
            value =
                    //  form all the possible hierarchies of categories:
                    "WITH RECURSIVE categoryHierarchy AS (" +
                    "SELECT id, name, CAST(name AS CHAR(255)) AS path " +
                    "FROM category " +
                    "WHERE parent_category_id IS NULL " +

                    "UNION ALL " +

                    "SELECT c.id, c.name, CONCAT(ch.path, ' - ', c.name) AS path " +
                    "FROM category c " +
                    "INNER JOIN categoryHierarchy ch " +
                    "ON c.parent_category_id = ch.id" +
                    ") " +

                    // retrieve the actual category hierarchies, excluding the utmost parent categories:
                    "SELECT ch1.id, ch1.path " +
                    "FROM categoryHierarchy ch1 " +
                    "WHERE NOT EXISTS (" +
                        "SELECT 1 " +
                        "FROM categoryHierarchy ch2 " +
                        "WHERE ch2.path <> ch1.path " +
                        "AND ch2.path LIKE CONCAT(ch1.path, '%') " +
                        ")"
            ,
            nativeQuery = true
    )
    List<Object[]> findAllCategoryHierarchies();

    /*
    result example:
    men
    women
    men - clothing
    men - clothing - tops
    ...
    */
    @Query(
            value =
                    // form all the possible hierarchies of categories
                    "WITH RECURSIVE categoryHierarchy AS (" +
                    "SELECT id, name, CAST(name AS CHAR(255)) AS path " +
                    "FROM category " +
                    "WHERE parent_category_id IS NULL " +

                    "UNION ALL " +

                    "SELECT c.id, c.name, CONCAT(ch.path, ' - ', c.name) AS path " +
                    "FROM category c " +
                    "INNER JOIN categoryHierarchy ch " +
                    "ON c.parent_category_id = ch.id" +
                    ") " +

                    // retrieve the actual category hierarchies
                    "SELECT id, path " +
                    "FROM categoryHierarchy ch1"
            ,
            nativeQuery = true
    )
    List<Object[]> findEachPossibleCategory();
}
