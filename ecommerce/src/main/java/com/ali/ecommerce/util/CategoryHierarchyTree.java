package com.ali.ecommerce.util;

import com.ali.ecommerce.DTO.databaseDTO.CategoryDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class CategoryHierarchyTree {
    private static final Logger log = LoggerFactory.getLogger(CategoryHierarchyTree.class);

//    private CategoryNode root;

    // build the category hierarchy tree and return it:
    public static List<CategoryNode> buildCategoryHierarchyTree(List<CategoryDTO> categories) {

        // the category node that we want to add its child category:
        CategoryNode categoryNode = new CategoryNode(null, new ArrayList<>());
        // create the root category node of the category hierarchy tree:
        CategoryNode root = categoryNode;

        // for each category path, split it into the categories of this path: e.g. "men - clothing - tops" -> ["men", "clothing", "tops"]
        for (CategoryDTO category : categories) {
            // split the category path into the categories of this path
            String path = category.getPath();
            String[] categoryNames = path.split(" - ");

            // for each resulting category of the above category path, add a new category node to the category hierarchy tree:
            for (int i = 0; i < categoryNames.length; i++) {

//                log.info("categoryName: {}", categoryName);

                // add a new category if it doesn't exist, and move on to this child category:
                categoryNode = categoryNode.getOrAddCategoryNode(
                        new CategoryDTO(i == categoryNames.length - 1 ? category.getId() : null, categoryNames[i])
                );
            }

            // refer back to the root category node to move on to the next category path:
            categoryNode = root;
        }

        return root.children;
    }
}
