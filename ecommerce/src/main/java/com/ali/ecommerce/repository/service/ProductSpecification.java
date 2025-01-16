package com.ali.ecommerce.repository.service;

import com.ali.ecommerce.model.Category;
import com.ali.ecommerce.model.Product;
import com.ali.ecommerce.model.QtyPerSizeAndColor;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {


  /* TODO: README file: the below criteria API method will solve the issue of the category
      table being of hierarchical structure, and the issue of having more than one
      selection parameter where some of them have array data type/structure, and the
      issue of handling the cases of these array parameters being empty or null
      */
    public static Specification<Product> filterProducts(
            List<String> categories,
            List<String> colors,
            List<String> sizes,
            List<double[]> priceRanges
    ) {
//        static method to reduce tight coupling between service layer and this class

        return (root, query, criteriaBuilder) -> {
//          - root is of type "Root". it represents the root of the
//            query. that is the "Product" table. it acts as the
//            starting point of the query or the phrase of the "FROM" clause
//            in the SQL query. it can also be used to join the "Product" table
//            with other tables
//          - query is of type "Query". it represents the query itself.
//          - criteriaBuilder is of type "CriteriaBuilder". it is used to
//            build the criteria for the query. that is the "WHERE" clause of the SQL query


            if (query != null) {
//          the "if" statement is to ensure the generated query object is not
//          null, and hence we can't apply any method on this null object
                query.distinct(true);
//          - the above line ensures all the rows returned by this query are
//            distinct (not unique).

                // - specifying the root (Product entity class in
                //   this case) columns to be selected
                query.multiselect(
                        root.get("id"),
                        root.get("name"),
                        root.get("description"),
                        root.get("qtyPerSizeAndColors"),
                        root.get("price"),
                        root.get("images")
//                        root.get("category")
                );

////                or:
//                // - specifying the root (Product entity class in
//                //   this case) columns to be selected, based on a DTO
//                //   class we create
//                query.multiselect(criteriaBuilder.construct(
//                        ProductDTO.class,
//                        root.get("id"),
//                        root.get("name"),
//                        root.get("price")
//                ));
            }




            List<Predicate> predicates = new ArrayList<>();

            //  category filter:
            if (categories != null && !categories.isEmpty()) {
                Join<Product, Category> categoryJoin = root.join("category", JoinType.INNER);
                // - the above means join the table "Product" and table "Category" to apply criteria on
                //   the category entities that are related to the product entities
                // - categoryJoin allows only accessing the values of the attributes of the related
                //   entities of the category table in relation to the product table.
                // - Join will result in having JOIN operation in the generated SQL query

                Predicate categoryPredicate = buildCategoryPredicate(categoryJoin, categories, criteriaBuilder);

                predicates.add(categoryPredicate);
            }

            //  price filter:
            if (priceRanges != null && !priceRanges.isEmpty()) {
                List<Predicate> pricePredicates = new ArrayList<>();

                for (double[] priceRange : priceRanges) {
                    /* TODO: developer-constraint: price range should be of length 1 or 2. and tell how
                        should case of range length 1 be handled
                        */
                    /* assert priceRange.length == 2 || priceRange.length == 1;*/

                    if (priceRange.length == 2) {
                        pricePredicates.add(
                                criteriaBuilder.
                                        between(root.get("price"), priceRange[0], priceRange[1])
                        );
                    } else if (priceRange.length == 1) {
                        pricePredicates.add(
                            criteriaBuilder.
                                    ge(root.get("price"), priceRange[0])
                        );
                    }
                    //  TODO: throw exception in case the length is neither 1 nor 2 for better code readability by other developers
                }

                if (!pricePredicates.isEmpty()) {
                    predicates.add(criteriaBuilder.or(pricePredicates.toArray(new Predicate[0])));
                    // - the above will apply the "OR" operation between all the elements of the pricePredicates Collection
                    // - toArray() method converts a Collection into an array. the parameter "new Predicate[0]"
                    //   means an array of size 0. this parameter is passed instead of "new Predicate[pricePredicates.size()]"
                    //   because this simplifies the code and because java implementations internally optimize the resulted
                    //   array to suit the size of the Collection
                }
            }

            //  color filter:
            if (colors != null && !colors.isEmpty()) {
                Path<QtyPerSizeAndColor> qtyPerSizeAndColorsPath = root.get("qtyPerSizeAndColors");

                predicates.add(criteriaBuilder.in(qtyPerSizeAndColorsPath.get("productColor")).value(colors));
                //  or:
                //  predicates.add(root.get("color").in(colors));
            }

            //  size filter:
            if (sizes != null && !sizes.isEmpty()) {
                Path<QtyPerSizeAndColor> qtyPerSizeAndColorsPath = root.get("qtyPerSizeAndColors");

                predicates.add(criteriaBuilder.in(qtyPerSizeAndColorsPath.get("productSize")).value(sizes));
                //  or:
                //  predicates.add(root.get("size").in(sizes));
            }

//            if(categories == null && colors == null && sizes == null && priceRanges == null) {
//                criteriaBuilder.conjunction(); // that is, no filtering
//            }
////            the above is just another way of handling the no filtering case


            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            //  the above will apply the "AND" operation between all the elements of the predicates Collection
        };
    }


    /* TODO: README file: the below criteria API method will solve the issue of the category
        table being of hierarchical structure, and the issue of having more than one
        selection parameter where some of them have array data type/structure, and the
        issue of handling the cases of these array parameters being empty or null
        */
    public static Specification<Product> filterProducts2(
            Long categoryId,
            List<String> colors,
            List<String> sizes,
            List<double[]> priceRanges
    ) {
//        static method to reduce tight coupling between service layer and this class

        return (root, query, criteriaBuilder) -> {
//          - root is of type "Root". it represents the root of the
//            query. that is the "Product" table. it acts as the
//            starting point of the query or the phrase of the "FROM" clause
//            in the SQL query. it can also be used to join the "Product" table
//            with other tables
//          - query is of type "Query". it represents the query itself.
//          - criteriaBuilder is of type "CriteriaBuilder". it is used to
//            build the criteria for the query. that is the "WHERE" clause of the SQL query


            if (query != null) {
//          the "if" statement is to ensure the generated query object is not
//          null, and hence we can't apply any method on this null object
                query.distinct(true);
//          - the above line ensures all the rows returned by this query are
//            distinct (not unique).

                // - specifying the root (Product entity class in
                //   this case) columns to be selected
                query.multiselect(
                        root.get("id"),
                        root.get("name"),
                        root.get("description"),
                        root.get("qtyPerSizeAndColors"),
                        root.get("price"),
                        root.get("images")
//                        root.get("category")
                );

////                or:
//                // - specifying the root (Product entity class in
//                //   this case) columns to be selected, based on a DTO
//                //   class we create
//                query.multiselect(criteriaBuilder.construct(
//                        ProductDTO.class,
//                        root.get("id"),
//                        root.get("name"),
//                        root.get("price")
//                ));
            }




            List<Predicate> predicates = new ArrayList<>();

            //  category filter:
            if (categoryId != null) {
                Join<Product, Category> categoryJoin = root.join("category", JoinType.INNER);
                // - the above means join the table "Product" and table "Category" to apply criteria on
                //   the category entities that are related to the product entities
                // - categoryJoin allows only accessing the values of the attributes of the related
                //   entities of the category table in relation to the product table.
                // - Join will result in having JOIN operation in the generated SQL query

                Predicate categoryPredicate = criteriaBuilder.equal(categoryJoin.get("id"), categoryId);

                predicates.add(categoryPredicate);
            }

            //  price filter:
            if (priceRanges != null && !priceRanges.isEmpty()) {
                List<Predicate> pricePredicates = new ArrayList<>();

                for (double[] priceRange : priceRanges) {
                    /* TODO: developer-constraint: price range should be of length 1 or 2. and tell how
                        should case of range length 1 be handled
                        */
                    /* assert priceRange.length == 2 || priceRange.length == 1;*/

                    if (priceRange.length == 2) {
                        pricePredicates.add(
                                criteriaBuilder.
                                        between(root.get("price"), priceRange[0], priceRange[1])
                        );
                    } else if (priceRange.length == 1) {
                        pricePredicates.add(
                                criteriaBuilder.
                                        ge(root.get("price"), priceRange[0])
                        );
                    }
                    //  TODO: throw exception in case the length is neither 1 nor 2 for better code readability by other developers
                }

                if (!pricePredicates.isEmpty()) {
                    predicates.add(criteriaBuilder.or(pricePredicates.toArray(new Predicate[0])));
                    // - the above will apply the "OR" operation between all the elements of the pricePredicates Collection
                    // - toArray() method converts a Collection into an array. the parameter "new Predicate[0]"
                    //   means an array of size 0. this parameter is passed instead of "new Predicate[pricePredicates.size()]"
                    //   because this simplifies the code and because java implementations internally optimize the resulted
                    //   array to suit the size of the Collection
                }
            }

            //  color filter:
            if (colors != null && !colors.isEmpty()) {
                Path<QtyPerSizeAndColor> qtyPerSizeAndColorsPath = root.get("qtyPerSizeAndColors");

                predicates.add(criteriaBuilder.in(qtyPerSizeAndColorsPath.get("productColor")).value(colors));
                //  or:
                //  predicates.add(root.get("color").in(colors));
            }

            //  size filter:
            if (sizes != null && !sizes.isEmpty()) {
                Path<QtyPerSizeAndColor> qtyPerSizeAndColorsPath = root.get("qtyPerSizeAndColors");

                predicates.add(criteriaBuilder.in(qtyPerSizeAndColorsPath.get("productSize")).value(sizes));
                //  or:
                //  predicates.add(root.get("size").in(sizes));
            }

//            if(categories == null && colors == null && sizes == null && priceRanges == null) {
//                criteriaBuilder.conjunction(); // that is, no filtering
//            }
////            the above is just another way of handling the no filtering case


            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            //  the above will apply the "AND" operation between all the elements of the predicates Collection
        };
    }

    private static Predicate buildCategoryPredicate(
            Path<Category> categoryPathRoot,
            //  - categoryPathRoot is the path(accessibility) to the category entities in the category table
            //    that are related to the product entities in the product table
            List<String> categoriesList,
            CriteriaBuilder criteriaBuilder
    ) {

//        Predicate currentPredicate = criteriaBuilder.conjunction();
        Predicate currentPredicate = null;
        Predicate currentCategoryNamePredicate = null;
        Predicate currentCategoryParentCategoryPredicate = null;
        Predicate currentCategoryNameAndParentCategoryPredicate = null;

        for (int i = categoriesList.size() - 1; i >= 0; i--) {

            String currentCategoryName = categoriesList.get(i);
//            String previousCategoryName = categoriesList.get(i - 1);
//            //  the above is a wrong to be added here, because for i = 0, it will throw an IndexOutOfBoundsException


            if (i == 0) {
                currentCategoryNamePredicate = criteriaBuilder.
                        equal(categoryPathRoot.get("name"), currentCategoryName);
//                currentCategoryParentCategoryPredicate = criteriaBuilder.
//                        equal(categoryPathRoot.get("parentCategory"), null);

                currentCategoryParentCategoryPredicate = criteriaBuilder.
                        isNull(categoryPathRoot.get("parentCategory"));
                //  - criteria API uses JPQL, and hence we must write "parentCategory" (that
                //    is, as data field) instead of "parent_category_id" (that is, as
                //    database attribute) above

                currentCategoryNameAndParentCategoryPredicate = criteriaBuilder.
                        and(currentCategoryNamePredicate, currentCategoryParentCategoryPredicate);

                currentPredicate = criteriaBuilder.
                        and(currentPredicate, currentCategoryNameAndParentCategoryPredicate);

            } else {
//                String nextCategoryName = categoriesList.get(i - 1);

                currentPredicate = criteriaBuilder.
                        equal(categoryPathRoot.get("name"), currentCategoryName);


                categoryPathRoot = categoryPathRoot.get("parentCategory");
                // - the above means get the rows/entities that are the values of the
                //   "parentCategory" data field of the current category entity.
                // - the above code line means move (when applying criteria/condition logic) to the
                //   row(s) of the parent category of the current category.
                // - changing the value of Path<> will result in having a JOIN operation in the
                //   generated SQL query


//                currentCategoryParentCategoryPredicate = criteriaBuilder.
//                        equal(categoryPathRoot.get("name"), nextCategoryName);

                /* TODO: version 2 of the above by using Subquery instead of
                    doing JOIN operation
                    */
//                Subquery<Category>

            }
            // - the above criteria will be translated into "if the category row has a certain name and
            //   its parent category row of this category row has a certain name, then select the products
            //   related to this category


        }

        return currentPredicate;
    }
    /* TODO: version 2 for the above by using EntityManager to get the criteria builder
         rather than using Specification
         */

}
