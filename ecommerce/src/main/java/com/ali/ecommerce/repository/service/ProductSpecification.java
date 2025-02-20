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
            // static method to reduce tight coupling between service layer and this class
            List<String> categories,
            List<String> colors,
            List<String> sizes,
            List<double[]> priceRanges
    ) {

        return (root, query, criteriaBuilder) -> {
            // avoid null pointer exception:
            if (query != null) {
                // all the rows returned by this query are distinct:
                query.distinct(true);

                // specifying the root columns to be selected:
                query.multiselect(
                        root.get("id"),
                        root.get("name"),
                        root.get("description"),
                        root.get("qtyPerSizeAndColors"),
                        root.get("price"),
                        root.get("images")
                        // root.get("category")
                );
            }

            List<Predicate> predicates = new ArrayList<>();

            //  category filter:
            if (categories != null && !categories.isEmpty()) {
                Join<Product, Category> categoryJoin = root.join("category", JoinType.INNER);

                Predicate categoryPredicate = buildCategoryPredicate(categoryJoin, categories, criteriaBuilder);

                predicates.add(categoryPredicate);
            }

            //  price filter:
            if (priceRanges != null && !priceRanges.isEmpty()) {
                List<Predicate> pricePredicates = new ArrayList<>();

                for (double[] priceRange : priceRanges) {
                    /* TODO: developer-constraint: price range should be of length 1 or 2. and tell how should case of range length 1 be handled*/
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
                    /* TODO: developer-constraint: throw exception in case the length is neither 1 nor 2 for better code readability by other developers*/
                }

                if (!pricePredicates.isEmpty()) {
                    predicates.add(criteriaBuilder.or(pricePredicates.toArray(new Predicate[0])));
                }
            }

            //  color filter:
            if (colors != null && !colors.isEmpty()) {
                Path<QtyPerSizeAndColor> qtyPerSizeAndColorsPath = root.get("qtyPerSizeAndColors");

                predicates.add(criteriaBuilder.in(qtyPerSizeAndColorsPath.get("productColor")).value(colors));
                //  predicates.add(root.get("color").in(colors));
            }

            //  size filter:
            if (sizes != null && !sizes.isEmpty()) {
                Path<QtyPerSizeAndColor> qtyPerSizeAndColorsPath = root.get("qtyPerSizeAndColors");

                predicates.add(criteriaBuilder.in(qtyPerSizeAndColorsPath.get("productSize")).value(sizes));
                //  predicates.add(root.get("size").in(sizes));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }


    /* TODO: README file: the below criteria API method will solve the issue of the category
        table being of hierarchical structure, and the issue of having more than one
        selection parameter where some of them have array data type/structure, and the
        issue of handling the cases of these array parameters being empty or null
        */
    public static Specification<Product> filterProducts2(
            // static method to reduce tight coupling between service layer and this class
            Long categoryId,
            List<String> colors,
            List<String> sizes,
            List<double[]> priceRanges
    ) {
        return (root, query, criteriaBuilder) -> {
            // avoid null pointer exception:
            if (query != null) {
                // all the rows returned by this query are distinct:
                query.distinct(true);

                // specifying the root columns to be selected:
                query.multiselect(
                        root.get("id"),
                        root.get("name"),
                        root.get("description"),
                        root.get("qtyPerSizeAndColors"),
                        root.get("price"),
                        root.get("images")
                        // root.get("category")
                );
            }

            List<Predicate> predicates = new ArrayList<>();

            //  category filter:
            if (categoryId != null) {
                Join<Product, Category> categoryJoin = root.join("category", JoinType.INNER);

                Predicate categoryPredicate = criteriaBuilder.equal(categoryJoin.get("id"), categoryId);

                predicates.add(categoryPredicate);
            }

            //  price filter:
            if (priceRanges != null && !priceRanges.isEmpty()) {
                List<Predicate> pricePredicates = new ArrayList<>();

                for (double[] priceRange : priceRanges) {
                    /* TODO: developer-constraint: price range should be of length 1 or 2. and tell how should case of range length 1 be handled*/
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
                    /* TODO: throw exception in case the length is neither 1 nor 2 for better code readability by other developers*/
                }

                if (!pricePredicates.isEmpty()) {
                    predicates.add(criteriaBuilder.or(pricePredicates.toArray(new Predicate[0])));
                }
            }

            //  color filter:
            if (colors != null && !colors.isEmpty()) {
                Path<QtyPerSizeAndColor> qtyPerSizeAndColorsPath = root.get("qtyPerSizeAndColors");

                predicates.add(criteriaBuilder.in(qtyPerSizeAndColorsPath.get("productColor")).value(colors));
                //  predicates.add(root.get("color").in(colors));
            }

            //  size filter:
            if (sizes != null && !sizes.isEmpty()) {
                Path<QtyPerSizeAndColor> qtyPerSizeAndColorsPath = root.get("qtyPerSizeAndColors");

                predicates.add(criteriaBuilder.in(qtyPerSizeAndColorsPath.get("productSize")).value(sizes));
                //  predicates.add(root.get("size").in(sizes));
            }

            /*
            if (categories == null && colors == null && sizes == null && priceRanges == null) {
                criteriaBuilder.conjunction(); // that is, no filtering
            }
            */
            // the above is just another way of handling the no filtering case


            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            //  the above will apply the "AND" operation between all the elements of the predicates Collection
        };
    }

    private static Predicate buildCategoryPredicate(
            Path<Category> categoryPathRoot,
            List<String> categoriesList,
            CriteriaBuilder criteriaBuilder
    ) {

        // Predicate currentPredicate = criteriaBuilder.conjunction();
        Predicate currentPredicate = null;
        Predicate currentCategoryNamePredicate = null;
        Predicate currentCategoryParentCategoryPredicate = null;
        Predicate currentCategoryNameAndParentCategoryPredicate = null;

        for (int i = categoriesList.size() - 1; i >= 0; i--) {

            String currentCategoryName = categoriesList.get(i);

            if (i == 0) {
                currentCategoryNamePredicate = criteriaBuilder.
                        equal(categoryPathRoot.get("name"), currentCategoryName);

                currentCategoryParentCategoryPredicate = criteriaBuilder.
                        isNull(categoryPathRoot.get("parentCategory"));

                currentCategoryNameAndParentCategoryPredicate = criteriaBuilder.
                        and(currentCategoryNamePredicate, currentCategoryParentCategoryPredicate);

                currentPredicate = criteriaBuilder.
                        and(currentPredicate, currentCategoryNameAndParentCategoryPredicate);

            } else {
                currentPredicate = criteriaBuilder.
                        equal(categoryPathRoot.get("name"), currentCategoryName);


                categoryPathRoot = categoryPathRoot.get("parentCategory");

                /* TODO: version 2 of the above by using Subquery instead of doing JOIN operation*/
                // Subquery<Category>
            }
        }
        return currentPredicate;
    }
    /* TODO: version 2 for the above by using EntityManager to get the criteria builder rather than using Specification*/

}
