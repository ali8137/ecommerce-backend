package com.ali.ecommerce.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Data
// this is an annotation from "lombok", it provides the getters and setters of the class "Student". it also provides toString(), hashCode() and equals() of this class
@NoArgsConstructor
@AllArgsConstructor
@Builder
// the above is optional. it is just for better code readability
public class Product {

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
            )
    )
    @Embedded
    // this data field being annotated with this annotation means that the class of this field is a weak entity class;
    // where there is no primary key for the class of this field as identifying this class is not important or needed.
    // in this case, the class of this field is typically not having a many-to-many or one-to-many relationships with other entity classes
    private List<QtyPerSize> qtyPerSizes;
    private Double price;
    @ElementCollection
    @CollectionTable(
            joinColumns = @JoinColumn(
                    name = "product_id"
            )
    )
    @Embedded
    private List<Image> images;
    @ManyToOne
    @JoinColumn(
        referencedColumnName = "id"
    )
    private Category category;
    private CartItem cartItem;
    private OrderItem orderItem;
    private List<Review> reviews;
    private List<Rating> productRatings;




    //    helper methods:
    public void addImage(Image image) {
        this.images.add(image);
    }

    public boolean isOutOfStock() {
        return this.qtyPerSizes.isEmpty();
    }

    public boolean isSmallOutOfStock() {
        return this.qtyPerSizes.stream()
                .anyMatch(qtyPerSize ->
                        qtyPerSize.getProductSize() == ProductSize.SMALL
                                && qtyPerSize.getSizeQuantity() == 0
                );
    }

    public boolean isMediumOutOfStock() {
        return this.qtyPerSizes.stream()
                .anyMatch(qtyPerSize ->
                        qtyPerSize.getProductSize() == ProductSize.MEDIUM
                                && qtyPerSize.getSizeQuantity() == 0
                );
    }

    public boolean isLargeOutOfStock() {
        return this.qtyPerSizes.stream()
                .anyMatch(qtyPerSize ->
                        qtyPerSize.getProductSize() == ProductSize.LARGE
                                && qtyPerSize.getSizeQuantity() == 0
                );
    }

    public void addReview(Review review) {
        this.reviews.add(review);
    }

    public void addRating(Rating rating) {
        this.productRatings.add(rating);
    }

}
