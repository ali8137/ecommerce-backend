package com.ali.ecommerce.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Data
// this is an annotation from "lombok", it provides the getters and setters of this class.
// it also provides toString(), hashCode() and equals() of this class
@NoArgsConstructor
@AllArgsConstructor
@Builder
// the above is optional. it is just for better code readability
public class Cart {

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
    private BigDecimal totalPrice = BigDecimal.ZERO;
//    BigDecimal is for critical accurate/precise calculations, where error is not tolerated.
//    Double is for when the calculation includes exact values (like currencies)
//  - we could have made the below relationship one-to-one rather than
//    one-to-many, because in our business logic, a user can only have one cart at a time.
    @JsonIgnore
//    added the above to fit with the business logic
    @ManyToOne(
//            cascade = CascadeType.ALL,
//            fetch = FetchType.LAZY
    )
    /* TODO: this relationship should have been better defined as one-to-one
        */
    @JoinColumn(
            referencedColumnName = "id"
    )
    private User user;
//    @JsonIgnore
//    //   - ignore one field when having a bidirectional mapping. better be the @OneToMany field
//    //   - related to the above note: not in this case, it is better not to have the above @JsonIgnore
//    //     annotation here based on the business logic
    @OneToMany(
            mappedBy = "cart",
            //   - without "mappedBy" property, spring data jpa will treat the below data field as
            //     a separate relationship than the relationship of the data field "cart" in the
            //     entity class "CartItem", and thus will create a relationship/join table for this
            //     one-to-many relationship
            cascade = CascadeType.ALL
            //          - the above property is usually added for the data field annotated with @OneToMany
            //          - for the CASCADE_ALL to work, we must first set the below "cartItem" data field of
            //            a Cart entity to be equal to the list of the CartItem entities that are
            //            related to this Cart entity
    )
    private List<CartItem> cartItems = new ArrayList<>();
//    better initialize the array data field to avoid null pointer exceptions when applying methods to it



//    helper methods:
////    removed the below to have a better and seamless design of CartItem class ---- beginning
//    public BigDecimal calculateTotalPrice() {
//        return this.cartItems.stream()
//                .map(cartItem ->
//                        cartItem.getPrice()
//                        //  - this note is wrong:
//                        //    new BigDecimal(String.valueOf(cartItem.getPrice()))
//                        //    or
//                        //    new BigDecimal(cartItem.getPrice().toString())
//                        //  - valueOf() method accepts as parameter any type of object and
//                        //    returns the string representation of that object
//                                .multiply(BigDecimal.valueOf(cartItem.calculateQuantity().longValue())))
//                                //  - or
//                                //    .multiply(new BigDecimal(cartItem.getQuantity().toString())))
//                //  - it was okay to use "new" keyword instantiation above
//                //    because we are directly passing this object
//                //    instance to the above method and hence it will
//                //    directly achieve its usage without the overhead of managing this object instance.
//                //    besides, this object instance is going to be used only in this
//                //    place and not in multiple places in the whole application. also,
//                //    there must be different object instances (in other words, prototypes and not singleton)
//                //    for each usage of this class "BigDecimal"
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//                //    the above reduce() method is equivalent to: reduce(new BigDecimal(0), (a, b) -> a.add(b));
//    }
////    removed the below to have a better and seamless design of CartItem class ---- end

    public void addCartItem(CartItem cartItem) {
        this.cartItems.add(cartItem);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Cart that = (Cart) o;
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
