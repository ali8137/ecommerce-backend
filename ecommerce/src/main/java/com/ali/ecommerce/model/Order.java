package com.ali.ecommerce.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Data
// this is an annotation from "lombok", it provides the getters and setters of this class.
// it also provides toString(), hashCode() and equals() of this class
//@NoArgsConstructor
@AllArgsConstructor
@Builder
// the above is optional. it is just for better code readability
public class Order {

//    spring data jpa deserialization of the JSON request:
    //    when a JSON request that contains a JSON object that corresponds to this entity class/table, and inside this JSON object,
    //    there are nested JSON objects that correspond to the entity classes/tables that are related to this entity class/table, is sent to a REST API;
    //    in this case, each JSON object will be converted/deserialized to the corresponding Java object.
    //    however, in the database, these resulted nested java objects won't be associated with the resulted java object of this entity class/table.
    //    to associate them with the resulted java object of this entity class/table,
    //    we should manually set these nested java objects to be associated with the resulted java object of this entity class/table.


    //    the no-args constructor should always be defined in entity classes

//    public Order() {
//        this.createdAt = LocalDateTime.now();
//        this.orderStatus = OrderStatus.CREATED;
//    }
    //    the above data fields values are better to be set in the service layer rather than the constructor above,
    //    this is for better separation of concerns/layers


    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private BigDecimal totalPrice;
//    BigDecimal is for critical accurate/precise calculations, where error is not tolerated.
//    Double is for when the calculation includes exact values (like currencies)
    private LocalDateTime orderDate;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;



//    helper methods:
    public BigDecimal calculateTotalPrice() {

    }


    public void addOrderItem(OrderItem orderItem) {

    }


//    private void setDelivered() {
//        this.orderStatus = OrderStatus.DELIVERED;
//    }
    //    this method is a helper method to set the order status to "delivered".
    //    but I believe setting it through the setter method of the OrderStatus enum is better.

}
