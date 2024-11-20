package com.ali.ecommerce.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Embeddable
// the above annotation is used when this entity classes is injected into another entity class as an injected dependency that is annotated with @Embedded.
// this class being annotated with this annotation means that this class is a weak entity class,
// where there is no primary key for this class as identifying this class is not important or needed.
// in this case, this class is typically not having a many-to-many, or one-to-many relationships with other entity classes
@Entity
@Data
// this is an annotation from "lombok", it provides the getters and setters of this entity class.
// it also provides toString(), hashCode() and equals() of this class
@NoArgsConstructor
@AllArgsConstructor
@Builder
// the above is optional. it is just for better code readability
public class Category {

//    spring data jpa deserialization of the JSON request:
    //    when a JSON request that contains a JSON object that corresponds to this entity class/table, and inside this JSON object,
    //    there are nested JSON objects that correspond to the entity classes/tables that are related to this entity class/table, is sent to a REST API;
    //    in this case, each JSON object will be converted/deserialized to the corresponding Java object.
    //    however, in the database, these resulted nested java objects won't be associated with the resulted java object of this entity class/table.
    //    to associate them with the resulted java object of this entity class/table,
    //    we should manually set these nested java objects to be associated with the resulted java object of this entity class/table.


    //    the no-args constructor should always be defined in entity classes...


    //    a constructor with a certain number of args could be defined in entity classes,
    //    for example, when we want to configure the object instance of this entity class to be initialized with certain values for its data fields.
    //    like for example, to set the initial state of the Order entity class...



//    the below data fields must all be classes/enums/records. primitive data types can't be used for the below data fields

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private String name;
    private String description;
    @ManyToOne
    @JoinColumn(
            referencedColumnName = "id"
    )
    private Category parentCategory;
    //  - if an entity/object instance/record has the above data field with value null, then
    //    this means this entity has no many-to-one relationship with another Category entity. and this means this entity
    //    has no parent category (that is, this entity is the ultimate/root/uttermost/utmost parent category itself
    //    with no parent category).
//    one-to-many side:
    private List<Category> subCategory;
    //  - if an entity/object instance/record has the above data field with value null, then
    //    this means this entity has no one-to-many relationship with another Category entity. and this means this entity
    //    has no subcategory (that is, this entity is the ultimate/root/uttermost/utmost subcategory itself
    //    with no further subcategory of it).
    private List<Product> products;




    //    helper methods:
    public void addSubCategory(Category category) {
        this.subCategory.add(category);
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }





//    Helper methods:


}
