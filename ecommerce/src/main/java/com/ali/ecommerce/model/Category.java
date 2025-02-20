package com.ali.ecommerce.model;


import com.ali.ecommerce.customAnnotaion.DescriptionAndSubCategoryConstraint;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
/* TODO: documentation: custom annotation: description should be null if the subCategory is not null or not empty:*/
@DescriptionAndSubCategoryConstraint
@Table(
    // no two categories with the same name and parent_category_id:
    uniqueConstraints = @UniqueConstraint(
            columnNames = {"name", "parent_category_id"}
    )
)
/* TODO: developer-constraint: each category should have unique (name, parent_category_id) tuple*/
public class Category {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private String name;
    /* TODO: developer-constraint: description should be null if the subCategory is not null or not empty*/
    private String description;
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(
            referencedColumnName = "id"
    )
    private Category parentCategory;
    @JsonIgnore
    @OneToMany(
            mappedBy = "parentCategory",
            cascade = CascadeType.ALL
    )
    private List<Category> subCategories;
    @ToString.Exclude
    @JsonIgnore
    @OneToMany(
            mappedBy = "category",
            cascade = CascadeType.ALL
    )
    private List<Product> products;




    //    helper methods:
    public void addSubCategory(Category category) {
        this.subCategories.add(category);
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Category that = (Category) o;
        return Objects.equals(this.id, that.id);
        //  return id.equals(that.id);
    }

    public int hashCode() {
        return Objects.hash(this.id);
    }
}
