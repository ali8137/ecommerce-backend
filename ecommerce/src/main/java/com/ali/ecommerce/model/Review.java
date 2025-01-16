package com.ali.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

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
public class Review
{

//    terms:
    //    ⁃ entity class: a class that is annotated with @Entity.
    //    ⁃ table: the table associated with this entity class.
    //    - entity: JPA managed object instances of this entity class. it will be persisted as a record in the database table
    //    - relationship annotation: @OneToOne, @OneToMany, @ManyToMany, etc.
    //    - relationship data fields: the data field of type B in entity class A where class B is related to class A. even if this data field is not annotated with any relationship annotation like @OneToOne, @OneToMany, etc.
    //    - self-referencing table: a table where records in this table reference to other records in this same table



//    spring data jpa deserialization of the JSON request:
    //    all the following notes are about the nested JSON objects of related tables:
    //    - when a JSON request that contains a JSON object that corresponds to this entity class/table, and inside this JSON object,
    //    there are nested JSON objects that correspond to the entity classes/tables that are related to this entity class/table, is sent to a REST API;
    //    in this case, each JSON object will be converted/deserialized to the corresponding Java object.
    //    however, in the database, these resulted nested java objects won't be associated with the resulted java object of this entity class/table.
    //    to associate them with the resulted java object of this entity class/table,
    //    we should manually set these nested java objects to be associated with the resulted java object of this entity class/table.
    //    related to the above 3 notes:
    //    ⁃ jpa concepts: in each relationship, there is owning side and inverse side
    //    ⁃ The owning side is the entity class that contains the data field annotated with @JoinColumn (in case of self-referencing table, that contains non-null data field annotated with @JoinColumn).
    //    This typically is the table that contains the foreign key in the relationship.
    //    The @JoinColumn data field (which is typically the foreign key) in This owning side must be set to be equal to the related/associated object instance (record) in the related table, which is an obvious thing,
    //    because the value of the foreign key must be set to reference the related/associated record in the other related table.

//    spring data jpa serialization of the JSON response:
    //    ⁃ Infinite loop happens in JPA during by serialization. And to prevent this, use @JsonIgnore or @JsonBackReference and @JsonManagedReference
    //    when a jackson library is serializing a java object to a JSON object,
    //    it will also serialize the related/associated java objects that have annotations like @OneToOne, @OneToMany, @ManyToMany, etc.
    //    this will create an infinite loop of serialization, where when serializing java object A,
    //    its related/annotated java object B will be serialized, and then this serialization of java object B will lead to serialization of its related/annotated java object A, and so on.
    //    ⁃ JSON serialization concept: there is primary and inverse side when serializing a java object/entity class and its related/annotated java objects/entity classes.
    //    ⁃ @JsonBackReference and @JsonManagedReference: inside a class A that contains a data field B (of the class B that is related to class A) annotated with @JsonBackReference.
    //    when serializing class A, the annotation @JsonManagedReference will tell to serialize the data field B annotated with it.
    //    on the other side, the class B that contains the data field A (of the class A that is related to class B) annotated with @JsonBackReference.
    //    when serializing class B, the annotation @JsonBackReference will tell NOT to serialize the data field A annotated with it.
    //    @JsonBackReference or @JsonIgnore are typically used for the table with the foreign key in the relationship.


//    spring data jpa self-referencing table:
    // ⁃ A self-referencing table is just one table where records in this table reference to other records in this same table. so, sub_category records reference to parent category records in the same table "Category"
    // ⁃ A self-referencing table can be alternatively designed as two tables instead, and these two tables have the same structure/attributes.
    //    for example, parent category and sub_category records can be represented/stored in separate Category and SubCategory entity classes instead of implementing them both by the same self-referencing class Category


//    spring data jpa entity class:
    // ⁃ Each object instance of entity class is a record in the table associated with this class. “Entity” word alone means record in JPA
    // ⁃ Inside entity class, each instance of this entity class, that is each record in this table, has a value for each data field annotated with relationship annotation. If the value of this data field is null, then this means that this record of the table is not related/associated/reference to or referenced by record(s) in another table or the same table( case of self-referencing table).


//    spring data jpa operations:
    //    persist: when creating a new table record and persisting it into the database
    //    remove: when deleting a table record and removing it from the database
    //    refresh: refresh operation refreshes the most up to-date state of a table record, that is to get all the updates done on this record due to other processes done against this record somewhere else. it also discards any unsaved changes to this record
    //    detach: will detach the table record from the database context, and hence any changes to this record won't be persisted to the database unless the table is merged back into the database context
    //    merge: will merge the table record to the database after detaching it
    //    check this chat with chatGPT about these operations https://chatgpt.com/share/67361537-168c-8013-9d7c-aca81566050d


//    associating/linking two entities of the related entity classes: --- check the section "persistence/flushing/saving of an entity into the database" below ---
    // ⁃ for persisting/saving entities:
    //   before persisting, we must set the relationship data field in the entity/object instance of the owner side entity class
    //   to refer/point to the entity/object instance of the related entity class.
    // ⁃ for setting the data fields of java object instances, when managed by spring data jpa:
    //   we have to set the relationship data field in the entity/object instance of the owner side entity class
    //   to refer/point to the entity/object instance of the related entity class. and we have to also to
    //   set the relationship data field in the related entity class to refer/point to the entity/object instance of the owner entity class.


//    stages of the lifecycle of an entity:
    // ⁃ transient state: creation of the entity/java object instance of the entity class
    // ⁃ linking this entity/java object instance of the entity class to the entity/java object instance of its related entity class
    //   look at the section "associating/linking two entities of the related entity classes"
    // ⁃ managed state: persisting this entity/java object instance of the entity class. in this stage, the entity becomes managed by spring data jpa,
    //   and any change to it will then in the next stage be persisted/saved to the database
    // ⁃ commiting of the transaction or flushing the changes to the database. this will save the changes to the database
    // - detached state: when the entity is detached from the database context,
    //   and hence any changes to this entity won't be persisted to the database - when commiting the transaction or flushing the changes - unless
    //   the entity is merged back into the database context
    // - merged state: when the entity is merged back into the database context
    // - removed state: when the entity is removed from the database context. this means the record of this
    //   entity is marked for deletion in the database during the next commiting of the transaction or flushing the changes to the database


//    persistence/flushing/saving of an entity into the database:
    // ⁃ if the entity to be persisted is the owner side:
    //   then persisting this entity will also persist the entity of the related entity class.
    //   but obviously before that, we must set the relationship data field in this owner side entity to refer/point to the entity/object instance of the related entity class.
    // ⁃ if the entity to be persisted is the inverse side:
    //   - then if the cascade attribute is set to persist/ALL:
    //     then persisting this entity will also persist the entity of the related entity class.
    //     and obviously after setting the relationship data field in the owner side entity to refer/point to the entity/object instance of the related entity class.
    //   - else if the cascade attribute is set to nothing:
    //     then persisting this entity won't also persist the entity of the related entity class. and hence in this case,
    //     we need to manually persist the entity/object instance of the inverse side entity class
    //     and persist the entity/object instance of the related entity class, which is the owner side entity class.
    //     and obviously after setting the relationship data field in the owner side entity to refer/point to the entity/object instance of the related entity class.



//    uni-directional vs bidirectional relationships between entity classes:
    // ⁃ in bidirectional:
    //   - setting he values of the relationship data fields in the related entity classes:
    //     - we need to set the value of the @JoinColumn annotation (for two reasons: to persist the related entities to the database,
    //       and to set the value of the data field of the java object instance - of the entity class containing this data field annotated with @JoinColumn annotation - in the in-memory of the backend server because the data fields annotated with relationship annotations like @ManyToOne, @OneToOne, ...
    //       are managed by spring data jpa, and hence unless annotated with relationship annotations,
    //       spring data jpa won't manage these annotated data fields and will ignore setting their values unlike data fields not annotated with relationship annotations.like @ManyToOne, @OneToOne, ...).
    //     - we need also to set the value of the other relationship data field that is present in the other related entity class.
    //       where this data field is annotated with the other relationship annotation, like @OneToMany, @OneToOne, ...
    //   - navigating/getting/retrieving the entity/object instance of the entity class from its related entity class:
    //     - we can use the getter method to get the relationship data field of type B of entity/object instance of the entity class A. where class A and class B are the related entity classes
    //     - we can use the getter method to get the relationship data field of type A of entity/object instance of the entity class B. where class A and class B are the related entity classes
    // ⁃ in uni-directional:
    //   - setting he values of the relationship data fields in the related entity classes:
    //     - we need only to set the value of the @JoinColumn annotation to persist the related entities to the database.
    //   - navigating/getting/retrieving the entity/object instance of the entity class from its related entity class:
    //     - we can use the getter method to get the relationship data field of type B of entity/object instance of the entity class A, where this data field is the annotated data field with the relationship annotation like @ManyToOne, @OneToOne, ... in this relationship.
    //     - and we can't use the getter method to get the other relationship data field that is not annotated with the relationship annotation like @ManyToOne, @OneToOne, ... in this relationship



//    miscellaneous notes I wrote in my iphone: --- these notes could be repetition of the notes above ---
    // ⁃ Entity class represents a table, entity represents a record in this table
    // ⁃ @JoinColumns determines the owning side of the relationship and determines the foreign key of the relationship
    // ⁃ Uni-directional mapping saves up memory by not having two references for the entities/object instances of
    //   related entity classes A and B. That is not to do both together, not to set the data field B in class A to refer/point to an
    //   entity/object instance of class B. And not to set the data field A in class B to
    //   refer/point to an entity/object instance of class A. Where of course class A and B must be related entities.
    // ⁃ Navigating in uni-directional vs bidirectional: navigating means to access/get/retrieve the
    //   value of the data field that is annotated with relationship annotations (@OneToMany, @ManyToOne, @ManyToMany, @OneToOne)
    // ⁃ Owning side (the class that contains the data field annotated with @JoinColumn) is the only side that is
    //   responsible for updating the foreign key column in the database.
    // ⁃ @JoinColumn data field "b" (of type "B") of an entity/object instance of an entity class "A" must be
    //   set to an entity/object instance of the related entity class "B". This value of this @JoinColumn data field will
    //   be taken by JPA during persistence
    //   stage (persistence of the data of an entity/object instance from the in-memory backend
    //   server (that is as java object instance in the memory) into the database (that is as record in the table)) to create a
    //   reference link (foreign key referencing) between the tables of the related entity classes A and B. Otherwise, the value of
    //   the foreign key specified inside this annotation @JoinColumn will have
    //   null value in record of the table in the database. Note that setting the data field a (of type A) of an entity/object
    //   instance of an entity class B to refer/point to an entity/object instance of the related entity class A will not be
    //   taken by JPA when persisting these two entities into the database, because this data field a
    //   is not annotated with @JoinColumn
    // ⁃ Bi-directional mapping is only about having the ability to get/access
    //   BOTH (and not only one, that is the one annotated with relationship annotation; Where this is
    //   the case of unidirectional mapping) relationship data fields of both related entity classes.
    // ⁃ Relationship data fields are data fields that are under JPA’s relationship management, and they are
    //   not like any other java data field. JPA knows that the owning side has a relationship data field, but it
    //   doesn't know about the existence of the relationship data field in the inverse side unless this data field
    //   was annotated with relationship annotation (@OneToMany, …).
    //   In other words, JPA knows that an object instance of class A (owning side, that is that
    //   contains @JoinColumn) has related object instance(s) of class B, but it doesn’t know
    //   that an object instance of class B (inverse side, that is that does not contain @JoinColumn) has
    //   related object instance(s) of class A unless the data field if class A inside class B is
    //   annotated with relationship annotation, because it is not JPA’s duty to keep track of the
    //   entity(s)/object instance(s) of the relationship data fields of both entity classes of the
    //   same relationship, it’s duty is to only keep track of just one of these data fields only, in
    //   order to associate/link the related/associated/referencing records of these
    //   related entity classes together in the database.
    // ⁃ Choosing which entity class is the Owning side and inverse side doesn't change the schema
    //   in the database, that is where the foreign key is in a relationship, …
    // ⁃ Persisting one of entities of the relationship or both of them is depended on whether
    //   the persisted entity is the owner side or not; then, in case of this entity being the inverse side, then
    //   the cascade property will determine whether we should persist both entities of the relationship or just one of them
    // ⁃ Java object graph

//    ------------------------------------------------------------



    //    the no-args constructor should always be defined in entity classes...


    //    - a constructor with a certain number of args could be defined in
    //    entity classes, for example, when we want to configure the object instance of this entity class to be initialized with
    //    certain values for its data fields. like for example, to set the initial state of the Order entity class...




    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private String reviewContent;
    @ManyToOne
    @JoinColumn(
            referencedColumnName = "id"
    )
    private User user;
    @ManyToOne
    @JoinColumn(
            referencedColumnName = "id"
    )
    private Product product;
    @JsonIgnore
    //    ignore one field when having a bidirectional mapping. better be the @OneToMany field
    @OneToMany(
            mappedBy = "review",
            //   - without "mappedBy" property, spring data jpa will treat the below data field as
            //     a separate relationship than the relationship of the data field "review" in the
            //     entity class "Rating", and thus will create a relationship/join table for this
            //     one-to-many relationship
            cascade = CascadeType.ALL
            //            the above property is usually added for the data field annotated with @OneToMany
    )
    private List<Rating> reviewRatings;




//    Helper methods

    public void addRating(Rating rating) {
        this.reviewRatings.add(rating);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Review that = (Review) o;
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

