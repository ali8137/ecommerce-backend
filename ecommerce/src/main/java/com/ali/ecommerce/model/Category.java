package com.ali.ecommerce.model;


import com.ali.ecommerce.customAnnotaion.DescriptionAndSubCategoryConstraint;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;


//@Embeddable
//// the above annotation is used when this entity classes is injected into another entity class as an injected dependency that is annotated with @Embedded.
//// this class being annotated with this annotation means that this class is a weak entity class,
//// where there is no primary key for this class as identifying this class is not important or needed.
//// in this case, this class is typically not having a many-to-many, or one-to-many relationships with other entity classes
@Entity
@Data
// this is an annotation from "lombok", it provides the getters and setters of this entity class.
// it also provides toString(), hashCode() and equals() of this class
@NoArgsConstructor
@AllArgsConstructor
@Builder
// the above is optional. it is just for better code readability
@DescriptionAndSubCategoryConstraint
// - the above is a custom annotation (description should be
//   null if the subCategory is not null or not empty)
@Table(
    uniqueConstraints = @UniqueConstraint(
            columnNames = {"name", "parent_category_id"}
    )
    //   - the above constraint ensures that there will be no
    //     two categories with the same name and parent_category_id
    //   - the above names are based on the names in the database and not in the
    //     entity class
)
/* TODO: developer-constraint: each category should have unique (name,
    parent_category_id) tuple
    */
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
//    @Column(
//        unique = true
//    )
    private String name;
    /* developer-constraint: description should be null if the
    subCategory is not null or not empty
    */
    private String description;
    @ToString.Exclude
//  - the above is to prevent infinite looping from happening by preventing the below data
//    field from being printed in the toString() method. where applying toString() on the
//    object instance A of this entity class Category will call also the toString() method on the
//    object instance B that is the parent category of this object instance A of this entity class
//    Category. and then this will call the toString() method on the object instance A that is the object
//    instance A which is the child category of the parent category which is the object instance B.
//  - note that logging using a logger (log.info(), ...) will call the toString() method implicitly
    @ManyToOne
    @JoinColumn(
            referencedColumnName = "id"
    )
    private Category parentCategory;
    //  - avoid self-referencing table schema design at all costs. it is not good with relational databases
    //  - to design a table that has hierarchy, like parentCategory, childCategory, grandchildCategory, etc.
    //    it is better to write the hierarchy as one data field/attribute/column in the database instead of
    //    referencing another column in another row in the same table. this attribute will consist of all
    //    the child properties in order concatenated together.for example, "menClothingTops", "womenClothingTops", etc...
    //  - if an entity/object instance/record has the above data field with value null, then
    //    this means this entity has no many-to-one relationship with another Category entity. and this means this entity
    //    has no parent category (that is, this entity is the ultimate/root/uttermost/utmost parent category itself
    //    with no parent category).
    @JsonIgnore
//    ignore one field when having a bidirectional mapping. better be the @OneToMany field
    @OneToMany(
            mappedBy = "parentCategory",
            //   - without "mappedBy" property, spring data jpa will treat the below data field as
            //     a separate relationship than the relationship of the data field "parentCategory" in the
            //     entity class "Category", and thus will create a relationship/join table for this
            //     one-to-many relationship
            cascade = CascadeType.ALL
            //            the above property is usually added for the data field annotated with @OneToMany
    )
    private List<Category> subCategories;
    //  - if an entity/object instance/record has the above data field with value null, then
    //    this means this entity has no one-to-many relationship with another Category entity. and this means this entity
    //    has no subcategory (that is, this entity is the ultimate/root/uttermost/utmost subcategory itself
    //    with no further subcategory of it).
    @ToString.Exclude
    //  - the above is to prevent infinite looping from happening by preventing the below data
    //    field from being printed in the toString() method. where applying toString() on the
    //    object instance A of this entity class Category will call also the toString() method on the
    //    object instance B of the below data field. and then this will call the toString() method
    //    on the object instance A of the data field "Category" in the Product class.
    //  - note that logging using a logger (log.info(), ...) will call the toString() method implicitly
    @JsonIgnore
//    ignore one field when having a bidirectional mapping. better be the @OneToMany field
    @OneToMany(
            mappedBy = "category",
            //   - without "mappedBy" property, spring data jpa will treat the below data field as
            //     a separate relationship than the relationship of the data field "category" in the
            //     entity class "Product", and thus will create a relationship/join table for this
            //     one-to-many relationship
            cascade = CascadeType.ALL
            //            the above property is usually added for the data field annotated with @OneToMany
    )
    private List<Product> products;




    //    helper methods:
    public void addSubCategory(Category category) {
        this.subCategories.add(category);
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Category that = (Category) o;
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
