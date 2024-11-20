package com.ali.ecommerce.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


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
    private BigDecimal totalPrice;
//    BigDecimal is for critical accurate/precise calculations, where error is not tolerated.
//    Double is for when the calculation includes exact values (like currencies)
    @ManyToOne(
//            cascade = CascadeType.ALL,
//            fetch = FetchType.LAZY
    )
    @JoinColumn(
            referencedColumnName = "id"
    )
    private User user;
    private List<CartItem> cartItems;



//    helper methods:
    public BigDecimal calculateTotalPrice() {
        return this.cartItems.stream()
                .map(cartItem ->
                        cartItem.getPrice()
                        //  - this note is wrong:
                        //    new BigDecimal(String.valueOf(cartItem.getPrice()))
                        //    or
                        //    new BigDecimal(cartItem.getPrice().toString())
                        //  - valueOf() method accepts as parameter any type of object and
                        //    returns the string representation of that object
                                .multiply(BigDecimal.valueOf(cartItem.getQuantity().longValue())))
                                //  - or
                                //    .multiply(new BigDecimal(cartItem.getQuantity().toString())))
                //  - it was okay to use "new" keyword instantiation above
                //    because we are directly passing this object
                //    instance to the above method and hence it will
                //    directly achieve its usage without the overhead of managing this object instance.
                //    besides, this object instance is going to be used only in this
                //    place and not in multiple places in the whole application. also,
                //    there must be different object instances (in other words, prototypes and not singleton)
                //    for each usage of this class "BigDecimal"
                .reduce(BigDecimal.ZERO, BigDecimal::add);
                //    the above reduce() method is equivalent to: reduce(new BigDecimal(0), (a, b) -> a.add(b));
    }

    public void addCartItem(CartItem cartItem) {
        this.cartItems.add(cartItem);
    }

}
