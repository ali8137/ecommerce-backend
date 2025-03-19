package com.ali.ecommerce.util;

import com.ali.ecommerce.DTO.databaseDTO.CategoryDTO;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Data
public class CategoryNode {
    private static final Logger log = LoggerFactory.getLogger(CategoryNode.class);
    private CategoryDTO categoryDTO;
    List<CategoryNode> children = new ArrayList<>();

    public CategoryNode(CategoryDTO categoryDTO, List<CategoryNode> children) {
        this.categoryDTO = categoryDTO;
        this.children = children;
    }

    // get or/and add a child category to a category node object instance:
    public CategoryNode getOrAddCategoryNode(CategoryDTO categoryDTO) {

//        log.info("categoryDTO: {}, children: {}", categoryDTO, children);

        // check if the category already exists:
        for (CategoryNode child : children) {
            if (child.categoryDTO.equals(categoryDTO)) {
                return child;
            }
        }

        // if the category does not exist, add it:
        CategoryNode child = new CategoryNode(categoryDTO, new ArrayList<>());
        /* TODO: we can choose to handle the case of the children being null here instead of making all the children of
             all the nodes in the category tree in the CategoryHierarchyTree class to be empty classes rather than null*/
        children.add(child);

        return child;
    }
}
