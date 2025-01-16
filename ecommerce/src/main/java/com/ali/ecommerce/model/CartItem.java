package com.ali.ecommerce.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;


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
//    private Integer quantity;
    @ElementCollection
    @CollectionTable(
            joinColumns = @JoinColumn(
                    name = "product_id"
            )
    )
    //    @Embedded
    //    the above annotation should not be added here, because the below data field is a collection of data type and not a single data type
    // this data field being annotated with this annotation means that the class of this field is a weak entity class;
    // where there is no primary key for the class of this field as identifying this class is not important or needed.
    // in this case, the class of this field is typically not having a many-to-many or one-to-many relationships with other entity classes
    private List<QtyPerSizeAndColor> qtyPerSizeAndColors;
    //  - alternative design of the above data field is to create a dedicated database table "QtyPerSizeAndColor",
    //    and this table will be referencing 2 other tables "QtyPerSize" and "QtyPerColor".
    //    I believe this design is the best.
    //  - another alternative design of the above data field is to create a dedicated database
    //    table "QtyPerSize" and this table will be referencing a single table "QtyPerColor"
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

    //  - the names of the helper methods better not to include prefix "get" and "set"
    //    to prevent confusion with the getter and setter methods of this entity class
    public Integer calculateQuantity() {
        return qtyPerSizeAndColors.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        CartItem that = (CartItem) o;
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
