package com.ali.ecommerce.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@Entity
@Data
// this is an annotation from "lombok", it provides the getters and setters of this class.
// it also provides toString(), hashCode() and equals() of this class
//@NoArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Builder
// the above is optional. it is just for better code readability
@Table(name = "`order`")
//you must add the above name `order` in order to escape the fact that the "order" (without the backticks) keyword in the SQL is a reserved keyword
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
    @JsonIgnore
    //    ignore one field when having a bidirectional mapping. better be the @OneToMany field
    @OneToMany(
            mappedBy = "order",
            //   - without "mappedBy" property, spring data jpa will treat the below data field as
            //     a separate relationship than the relationship of the data field "order" in the
            //     entity class "OrderItem", and thus will create a relationship/join table for this
            //     one-to-many relationship
            cascade = CascadeType.ALL
            //            the above property is usually added for the data field annotated with @OneToMany
    )
    private List<OrderItem> orderItems;
//    // - we can choose to remove the below data fields in this side of the relationship,
//    //   thus having a unidirectional relationship/mapping
//    @OneToOne
//    private Payment payment;



//    helper methods:
//    public BigDecimal calculateTotalPrice() {
//
//    }


    public void addOrderItem(OrderItem orderItem) {

    }


//    private void setDelivered() {
//        this.orderStatus = OrderStatus.DELIVERED;
//    }
    //    this method is a helper method to set the order status to "delivered".
    //    but I believe setting it through the setter method of the OrderStatus enum is better.

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Order that = (Order) o;
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
