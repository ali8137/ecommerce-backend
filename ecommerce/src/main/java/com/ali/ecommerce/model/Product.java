package com.ali.ecommerce.model;


import com.ali.ecommerce.customAnnotaion.ProductCategoryConstraint;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Entity
@Data
// this is an annotation from "lombok", it provides the getters and setters of the class "Student". it also provides toString(), hashCode() and equals() of this class
@NoArgsConstructor
@AllArgsConstructor
@Builder
// the above is optional. it is just for better code readability
@ProductCategoryConstraint
public class Product {
    /* TODO: database design: for better database design, there should be a separate table A for the below data
        fields without the qtyPerSizeAndColors data field. and another table B (the real product table) which
        inherits table A, this table contains qtyPerSizeAndColors data field. the tables CartItem and OrderItem should
        should be related to table A in this case */

//    spring data jpa deserialization of the JSON request:
    //    when a JSON request that contains a JSON object that corresponds to this entity class/table, and inside this JSON object,
    //    there are nested JSON objects that correspond to the entity classes/tables that are related to this entity class/table, is sent to a REST API;
    //    in this case, each JSON object will be converted/deserialized to the corresponding Java object.
    //    however, in the database, these resulted nested java objects won't be associated with the resulted java object of this entity class/table.
    //    to associate them with the resulted java object of this entity class/table,
    //    we should manually set these nested java objects to be associated with the resulted java object of this entity class/table.


    //    the no-args constructor should always be defined in entity classes

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
//                    the above names are based on the names in the database and not in the entity class
            )
            /* TODO: developer-constraint: each QtyPerSizeAndColor row should have
                unique (product_id, product_size and product_color) tuple
                */
    )
//    @Embedded
    //    the above annotation should not be added here, because the below data field is a collection of data type and not a single data type
    // this data field being annotated with this annotation means that the class of this field is a weak entity class;
    // where there is no primary key for the class of this field as identifying this class is not important or needed.
    // in this case, the class of this field is typically not having a many-to-many or one-to-many relationships with other entity classes
    private List<QtyPerSizeAndColor> qtyPerSizeAndColors;
    //  - the above data field should be better named as "qtiesPerSizeAndColor".
    //  - alternative design of the above data field is to create a dedicated database table "QtyPerSizeAndColor",
    //    and this table will be referencing 2 other tables "QtyPerSize" and "QtyPerColor".
    //    I believe this design is the best.
    //  - another alternative design of the above data field is to create a dedicated database
    //    table "QtyPerSize" and this table will be referencing a single table "QtyPerColor"
    private Double price;
    @ElementCollection
    @CollectionTable(
            joinColumns = @JoinColumn(
                    name = "product_id"
            )
    )
//    @Embedded
    //    the above annotation should not be added here, because the below data field is a collection of data type and not a single data type
    private List<Image> images;
    @NotNull(message = "category of a product cannot be null")
    @ManyToOne
    @JoinColumn(
        referencedColumnName = "id"
    )
    /* developer-constraint: product entity should be related
    to the most child category (which has no child categories)
    */
    private Category category;
//    // - we can choose to remove the below data fields in this side of the relationship,
//    //   thus having a unidirectional relationship/mapping
//    @OneToOne
//    private CartItem cartItem;
//    @OneToOne
//    private OrderItem orderItem;
    @JsonIgnore
    //    ignore one field when having a bidirectional mapping. better be the @OneToMany field
    @OneToMany(
            mappedBy = "product",
            //   - without "mappedBy" property, spring data jpa will treat the below data field as
            //     a separate relationship than the relationship of the data field "product" in the
            //     entity class "Review", and thus will create a relationship/join table for this
            //     one-to-many relationship
            cascade = CascadeType.ALL
            //            the above property is usually added for the data field annotated with @OneToMany
    )
    private List<Review> reviews;
    @JsonIgnore
    //    ignore one field when having a bidirectional mapping. better be the @OneToMany field
    @OneToMany(
            mappedBy = "product",
            //   - without "mappedBy" property, spring data jpa will treat the below data field as
            //     a separate relationship than the relationship of the data field "product" in the
            //     entity class "Rating", and thus will create a relationship/join table for this
            //     one-to-many relationship
            cascade = CascadeType.ALL
            //            the above property is usually added for the data field annotated with @OneToMany
    )
    private List<Rating> productRatings;




    //    helper methods:
    public void addImage(Image image) {
        this.images.add(image);
    }

////  - previous version of these methods corresponding to the previous design of
////    the data field qtyPerSizeAndColors:
//    public boolean isOutOfStock() {
//        return this.qtyPerSizes.isEmpty();
//    }
//
//    public boolean isSmallOutOfStock() {
//        return this.qtyPerSizes.stream()
//                .anyMatch(qtyPerSize ->
//                        qtyPerSize.getProductSize() == ProductSize.SMALL
//                                && qtyPerSize.getSizeQuantity() == 0
//                );
//    }
//
//    public boolean isMediumOutOfStock() {
//        return this.qtyPerSizes.stream()
//                .anyMatch(qtyPerSize ->
//                        qtyPerSize.getProductSize() == ProductSize.MEDIUM
//                                && qtyPerSize.getSizeQuantity() == 0
//                );
//    }
//
//    public boolean isLargeOutOfStock() {
//        return this.qtyPerSizes.stream()
//                .anyMatch(qtyPerSize ->
//                        qtyPerSize.getProductSize() == ProductSize.LARGE
//                                && qtyPerSize.getSizeQuantity() == 0
//                );
//    }

////    design 2 of the data field qtyPerSizeAndColors -- version 1:
//    public boolean isOutOfStock() {
//        return this.qtyPerSizeAndColors.isEmpty();
//    }
//
//    public boolean isSmallOutOfStock() {
//        return this.qtyPerSizeAndColors.stream()
//                .noneMatch(qtyPerSize ->
//                        qtyPerSize.getProductSize() == ProductSize.SMALL
//                );
//    }
//
//    public boolean isMediumOutOfStock() {
//        return this.qtyPerSizeAndColors.stream()
//                .noneMatch(qtyPerSize ->
//                        qtyPerSize.getProductSize() == ProductSize.MEDIUM
//                );
//    }
//
//    public boolean isLargeOutOfStock() {
//        return this.qtyPerSizeAndColors.stream()
//                .noneMatch(qtyPerSize ->
//                        qtyPerSize.getProductSize() == ProductSize.LARGE
//                );
//    }
//
//    public boolean isWhiteOutOfStock() {
//        return this.qtyPerSizeAndColors.stream()
//                .noneMatch(qtyPerColor ->
//                        qtyPerColor.getProductColor() == ProductColor.WHITE
//                );
//    }
//
//    public boolean isBlackOutOfStock() {
//        return this.qtyPerSizeAndColors.stream()
//                .noneMatch(qtyPerColor ->
//                        qtyPerColor.getProductColor() == ProductColor.BLACK
//                );
//    }
//
//    public boolean isYellowOutOfStock() {
//        return this.qtyPerSizeAndColors.stream()
//                .noneMatch(qtyPerColor ->
//                        qtyPerColor.getProductColor() == ProductColor.YELLOW
//                );
//    }

//    design 2 of the data field qtyPerSizeAndColors -- version 2:
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

//    //  - note: the database SQL query won't work here for adding new
//    //    records of the data field qtyPerSizeAndColors, because in this design, the class
//    //    QtyPerSizeAndColor is not a table (we could have made it to be)
//    public void addQtyPerSizeAndColor(QtyPerSizeAndColor qtyPerSizeAndColor) {
//
//        var qtyOptional = findQtyPerSizeAndColor(qtyPerSizeAndColor);
//
//        if (qtyOptional.isPresent()) {
//            var qty = qtyOptional.get();
//
//            qty.setQuantity(
//                    qty.getQuantity() + qtyPerSizeAndColor.getQuantity()
//            );
//
//            return;
//        }
//
//        this.qtyPerSizeAndColors.add(qtyPerSizeAndColor);
//    }
//
//    public void decrementQtyPerSizeAndColor(QtyPerSizeAndColor qtyPerSizeAndColor) {
//
//    }
//
//    public void removeQtyPerSizeAndColor(QtyPerSizeAndColor qtyPerSizeAndColor) {
//        Optional<QtyPerSizeAndColor> qtyOptional = findQtyPerSizeAndColor(qtyPerSizeAndColor);
//
//        qtyOptional.ifPresent(qty -> this.qtyPerSizeAndColors.remove(qty));
//    }
//
//    private Optional<QtyPerSizeAndColor> findQtyPerSizeAndColor(QtyPerSizeAndColor qtyPerSizeAndColor) {
//        return this.qtyPerSizeAndColors.stream()
//                .filter(qtyPerSizeAndColorElement ->
//                        qtyPerSizeAndColorElement.getProductSize()
//                                .equals(qtyPerSizeAndColor.getProductSize())
//                                && qtyPerSizeAndColorElement.getProductColor()
//                                .equals(qtyPerSizeAndColor.getProductColor()))
//                .findFirst();
//    }
//    ------------------------------


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
        //  //  or
        //  return id.equals(that.id);
    }
    //  - the above is to override the equals method to state that two categories with the same
    //    id are equal. this is a good practice for entity classes in JPA. and since we overrode
    //    equals() method, we should also override hashCode() method.

    public int hashCode() {
        return Objects.hash(this.id);
    }
    //    the above means the hashing of this entity class will be based on the id of this
    //    entity class instead of the object reference. so, in a HashMap or a HashSet, two
    //    TemplateEntityClass having the same id will be considered equal. and hence in the case of
    //    HashSet, it will only keep one of them when adding the two of them.

}
