package com.ali.ecommerce.repository;

import com.ali.ecommerce.DTO.databaseDTO.CategoryDTO;
import com.ali.ecommerce.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {


//    methods with functionalities based on its name structure:

    boolean existsByName(String categoryName);

//    public EntityClass1 method1(ClassName2 obj2);
//
//    public Optional<EntityClass1> method2(ClassName3 obj3);
//
//    public List<EntityClass1> method3(ClassName4 obj4);
//
//    Page<Product> findAllByName(Pageable pageable);
//    //    wrapping Page inside Optional is unnecessary, because page can never be null


//    JPQL queries:


//    native SQL queries: methods with functionalities based on the SQL query specified in the annotation:

    @Query(
            value = "WITH RECURSIVE categoryHierarchy AS (" +
                    "SELECT id, name, CAST(name AS CHAR(255)) AS path " +
                    "FROM category " +
                    "WHERE parent_category_id IS NULL " +

                    "UNION ALL " +

                //  "SELECT ch.id, ch.name, CONCAT(ch.path, ' - ', ch.name) AS path " +
                //  the above is wrong. using ch attributes instead of c attributes will lead to infinite loop
                    "SELECT c.id, c.name, CONCAT(ch.path, ' - ', c.name) AS path " +
                    "FROM category c " +
                    "INNER JOIN categoryHierarchy ch " +
                    "ON c.parent_category_id = ch.id" +
                    ") " +
                    //  the above will form all the possible hierarchies of categories

                    "SELECT ch1.id, ch1.path " +
                    "FROM categoryHierarchy ch1 " +
                    "WHERE NOT EXISTS (" +
                        "SELECT 1 " +
                        "FROM categoryHierarchy ch2 " +
                        "WHERE ch2.path <> ch1.path " +
//                        "AND ch2.path LIKE CONCAT('%', ch1.path, '%') " +
                        "AND ch2.path LIKE CONCAT(ch1.path, '%') " +
                        ")"
                        //  the above will just take the values of path column
                        //  from categoryHierarchy table that are not substrings of any
                        //  other value of the path column in the same table. in other
                        //  words, it will return only the correct category hierarchies
//            result example:
//            men - clothing - tops
//            women - clothing - tops
//            ...
            ,
            nativeQuery = true
    )
    List<Object[]> findAllCategoryHierarchies();
//  - each table row retrieved from the database is an array (Object[]) of "Object"
//    object instances, where the elements of this array are attributes. the types of
//    these elements are the same types of the data fields of the corresponding table
//    being fetched in case of using spring data JPA (and thus JPA entity mapping). and
//    are the same type of the attributes in the database in case of not having spring
//    data JPA (and thus JPA entity mapping)


    @Query(
            value = "WITH RECURSIVE categoryHierarchy AS (" +
                    "SELECT id, name, CAST(name AS CHAR(255)) AS path " +
                    "FROM category " +
                    "WHERE parent_category_id IS NULL " +

                    "UNION ALL " +

                    "SELECT c.id, c.name, CONCAT(ch.path, ' - ', c.name) AS path " +
                    "FROM category c " +
                    "INNER JOIN categoryHierarchy ch " +
                    "ON c.parent_category_id = ch.id" +
                    ") " +
                    //  the above will form all the possible hierarchies of categories

                    "SELECT id, path " +
                    "FROM categoryHierarchy ch1"

//            result example:
//            men
//            women
//            men - clothing
//            men - clothing - tops
//            ...
            ,
            nativeQuery = true
    )
    List<Object[]> findEachPossibleCategory();


//    @Query(value = "SELECT * FROM table_name", nativeQuery = true)
//    public List<EntityClass1> method4();
//
//    @Query(
//            value = "",
//            nativeQuery = true
//    )
//    Page<Product> method1();
//    //    wrapping Page inside Optional is unnecessary, because page can never be null


//    ---------------------------------------------------------------------------

//    @Query(
//            value = "insert into category_names (name) values (:categoryName)",
//            nativeQuery = true
//    )
//    @Modifying
//    void method2(String categoryName);
//
//
//
//    @Modifying
//    @Transactional
//    //    @Transactional is needed if the method is an updating method
//    @Query("UPDATE User u SET u.email = :email WHERE u.id = :id")
//    void updateEmailById(@Param("id") Long id, @Param("email") String email);
//
//
//
//
//    @Procedure(name = "procedureName1")
//        //    or
//        //@Query(
//        //        value = "CALL procedureName1()",
//        //        nativeQuery = true
//        //)
//    void executeStoredProcedure1();
//
//
//    @Query(
//            value = "",
//            nativeQuery = true
//    )
//    @Modifying
//    void changingDataMethod1();


//    ---------------------------------------------------------------------------


//  - stored procedures, functions, triggers, views, ... must be created in the database and not here.
//  - in case you need to execute a complex SQL query that results in a table which is not mapped to
//    any entity class, then don't use spring data JPA and use instead either EntityManager or JdbcTemplate.
//    also, when the query is applied on a table that is not an entity class like a temporary table, ... then it is
//    better to either use EntityManager or JdbcTemplate
}
