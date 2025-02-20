package com.ali.ecommerce.model;


import com.ali.ecommerce.customAnnotaion.ProductCategoryConstraint;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ProductCategoryConstraint
public class Product {
    /* TODO: database design: for better database design, there should be a separate table A for the below data fields without the qtyPerSizeAndColors data field. and another table B (the real product table) which inherits table A, this table contains qtyPerSizeAndColors data field. the tables CartItem and OrderItem should should be related to table A in this case */
    // EDIT (THIS NOTE DOES NOT ACTUALLY APPLY) related to the above note: i have changed the design of the entity class CartItem and OrderItem, and hence the above_TODO became unnecessary
    /* TODO: developer-constraint: when a color or a size of this product is to be removed, check if this color or size is the only last available color or size of this product. if so, remove this product*/

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private String name;
    private String description;
    @ElementCollection
    @CollectionTable(
            joinColumns = @JoinColumn(
                    name = "product_id"
            ),
            uniqueConstraints = @UniqueConstraint(
                    columnNames = {"product_id", "product_size", "product_color"}
            )
            /* TODO: developer-constraint: each QtyPerSizeAndColor row should have unique (product_id, product_size and product_color) tuple*/
    )
    private List<QtyPerSizeAndColor> qtyPerSizeAndColors;
    private BigDecimal price;
    @ElementCollection
    @CollectionTable(
            joinColumns = @JoinColumn(
                    name = "product_id"
            )
    )
    private List<Image> images;
    @NotNull(message = "category of a product cannot be null")
    @ManyToOne
    @JoinColumn(
        referencedColumnName = "id"
    )
    /* TODO: developer-constraint: product entity should be related to the most child category (which has no child categories)*/
    private Category category;
    @JsonIgnore
    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL
    )
    private List<Review> reviews;
    @JsonIgnore
    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL
    )
    private List<Rating> productRatings;


    //    helper methods:
    public void addImage(Image image) {
        this.images.add(image);
    }

//    note: the database SQL query is better for checking stock status
    public boolean isOutOfStock() {
        return this.qtyPerSizeAndColors.isEmpty();
    }

    public boolean isSizeOutOfStock(ProductSize productSize) {
        return this.qtyPerSizeAndColors.stream()
                .noneMatch(qtyPerSize ->
                        qtyPerSize.getProductSize() == ProductSize.valueOf(productSize.name())
                );
    }

    public boolean isColorOutOfStock(ProductColor productColor) {
        return this.qtyPerSizeAndColors.stream()
                .noneMatch(qtyPerColor ->
                        qtyPerColor.getProductColor() == ProductColor.valueOf(productColor.name())
                );
    }

    public void addReview(Review review) {
        review.setProduct(this);
        this.reviews.add(review);
    }

    public void addRating(Rating rating) {
        rating.setProduct(this);
        this.productRatings.add(rating);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Product that = (Product) o;
        return Objects.equals(this.id, that.id);
        //  return id.equals(that.id);
    }

    public int hashCode() {
        return Objects.hash(this.id);
    }
}
