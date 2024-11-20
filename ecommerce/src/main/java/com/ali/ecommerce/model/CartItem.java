package com.ali.ecommerce.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Entity
@Data
// this is an annotation from "lombok", it provides the getters and setters of this entity class.
// it also provides toString(), hashCode() and equals() of this class
@NoArgsConstructor
@AllArgsConstructor
@Builder
// the above is optional. it is just for better code readability
public class CartItem {

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
    private BigDecimal price;
    private Integer quantity;
    @ManyToOne
    @JoinColumn(
            referencedColumnName = "id"
    )
    private Cart cart;
    @OneToOne
    @JoinColumn(
            referencedColumnName = "id"
    )
    private Product product;


    //    helper methods:

}
