//package com.ali.ecommerce.model;
//
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.util.Objects;
//
//
//@Embeddable
//    // the above annotation is used when this entity classes is injected into another entity class as an injected dependency that is annotated with @Embedded.
//    // this class being annotated with this annotation means that this class is a weak entity class,
//    // where there is no primary key for this class as identifying this class is not important or needed.
//    // in this case, this class is typically not having a many-to-many, or one-to-many relationships with other entity classes
//
//@Entity
//
////@Table(
////        name = "tableName1",
////        uniqueConstraints = {
////                @UniqueConstraint(name = "uniqueConstraintName1", columnNames = "columnName1"),
////                @UniqueConstraint(name = "uniqueConstraintName2", columnNames = "columnName2")
////        }
////)
//
//@Data
//    // this is an annotation from "lombok", it provides the getters and setters of this entity class.
//    // it also provides toString(), hashCode() and equals() of this class
//
////@Getter
////@Setter
//
//@NoArgsConstructor
//@AllArgsConstructor
//
//@Builder
//    // the above is optional. it is just for better code readability
//
////@ToString(exclude = "excludedField1")
//    // the above is to resolve a certain issue related to testing
//
//@AttributeOverrides({
//        @AttributeOverride(
//                name = "overrideName1",
//                column = @Column(name = "columnName1")
//        ),
//        @AttributeOverride(
//                name = "overrideName2",
//                column = @Column(name = "columnName2")
//        ),
//        @AttributeOverride(
//                name = "overrideName3",
//                column = @Column(name = "columnName3")
//        )
//            //        the above overrides change the names of certain columns in the table of this entity class "User" to the above names
//})
//public class TemplateEntityClass
//        //  - or
//        //    public class User implements UserDetails
//        //  - the above will allow to implement the UserDetails interface, thus allowing to override and use the methods of that interface.
//        //  - these methods are getUsername(), getPassword(), isEnabled(), isAccountNonExpired(), isAccountNonLocked(), isCredentialsNonExpired(), getAuthorities()
//        //  - "implements UserDetails" was only used to make it easier to implement the method of
//        //    the UserDetailsService interface with just retrieving the user details from the
//        //    database as User which is an implementation of UserDetails
//        //  - or
//        //    public class User extends User
//        //  - or
//        //    public class TemplateEntityClass
//        //  - or
//        //    public record TemplateEntityClass(){}
//{
//
////    terms:
//    //    ⁃ entity class: a class that is annotated with @Entity.
//    //    ⁃ table: the table associated with this entity class.
//    //    - entity: JPA managed object instances of this entity class. it will be persisted as a record in the database table
//    //    - relationship annotation: @OneToOne, @OneToMany, @ManyToMany, etc.
//    //    - relationship data fields: the data field of type B in entity class A where class B is related to class A. even if this data field is not annotated with any relationship annotation like @OneToOne, @OneToMany, etc.
//    //    - self-referencing table: a table where records in this table reference to other records in this same table
//
//
//
////    spring data jpa deserialization of the JSON request:
//    //    all the following notes are about the nested JSON objects of related tables:
//        //    - when a JSON request that contains a JSON object that corresponds to this entity class/table, and inside this JSON object,
//        //    there are nested JSON objects that correspond to the entity classes/tables that are related to this entity class/table, is sent to a REST API;
//        //    in this case, each JSON object will be converted/deserialized to the corresponding Java object.
//        //    however, in the database, these resulted nested java objects won't be associated with the resulted java object of this entity class/table.
//        //    to associate them with the resulted java object of this entity class/table,
//        //    we should manually set these nested java objects to be associated with the resulted java object of this entity class/table.
//        //    related to the above 3 notes:
//            //    ⁃ jpa concepts: in each relationship, there is owning side and inverse side
//            //    ⁃ The owning side is the entity class that contains the data field annotated with @JoinColumn (in case of self-referencing table, that contains non-null data field annotated with @JoinColumn).
//                //    This typically is the table that contains the foreign key in the relationship.
//                //    The @JoinColumn data field (which is typically the foreign key) in This owning side must be set to be equal to the related/associated object instance (record) in the related table, which is an obvious thing,
//                //    because the value of the foreign key must be set to reference the related/associated record in the other related table.
//
////    spring data jpa serialization of the JSON response:
//    //    ⁃ Infinite loop happens in JPA during by serialization. And to prevent this, use @JsonIgnore or @JsonBackReference and @JsonManagedReference
//    //    when a jackson library is serializing a java object to a JSON object,
//    //    it will also serialize the related/associated java objects that have annotations like @OneToOne, @OneToMany, @ManyToMany, etc.
//    //    this will create an infinite loop of serialization, where when serializing java object A,
//    //    its related/annotated java object B will be serialized, and then this serialization of java object B will lead to serialization of its related/annotated java object A, and so on.
//    //    ⁃ JSON serialization concept: there is primary and inverse side when serializing a java object/entity class and its related/annotated java objects/entity classes.
//    //    ⁃ @JsonBackReference and @JsonManagedReference: inside a class A that contains a data field B (of the class B that is related to class A) annotated with @JsonBackReference.
//    //    when serializing class A, the annotation @JsonManagedReference will tell to serialize the data field B annotated with it.
//    //    on the other side, the class B that contains the data field A (of the class A that is related to class B) annotated with @JsonBackReference.
//    //    when serializing class B, the annotation @JsonBackReference will tell NOT to serialize the data field A annotated with it.
//    //    @JsonBackReference or @JsonIgnore are typically used for the table with the foreign key in the relationship.
//
//
////    spring data jpa self-referencing table:
//    // ⁃ A self-referencing table is just one table where records in this table reference to other records in this same table. so, sub_category records reference to parent category records in the same table "Category"
//    // ⁃ A self-referencing table can be alternatively designed as two tables instead, and these two tables have the same structure/attributes.
//    //    for example, parent category and sub_category records can be represented/stored in separate Category and SubCategory entity classes instead of implementing them both by the same self-referencing class Category
//
//
////    spring data jpa entity class:
//    // ⁃ Each object instance of entity class is a record in the table associated with this class. “Entity” word alone means record in JPA
//    // ⁃ Inside entity class, each instance of this entity class, that is each record in this table, has a value for each data field annotated with relationship annotation. If the value of this data field is null, then this means that this record of the table is not related/associated/reference to or referenced by record(s) in another table or the same table( case of self-referencing table).
//
//
////    spring data jpa operations:
//    //    persist: when creating a new table record and persisting it into the database
//    //    remove: when deleting a table record and removing it from the database
//    //    refresh: refresh operation refreshes the most up to-date state of a table record, that is to get all the updates done on this record due to other processes done against this record somewhere else. it also discards any unsaved changes to this record
//    //    detach: will detach the table record from the database context, and hence any changes to this record won't be persisted to the database unless the table is merged back into the database context
//    //    merge: will merge the table record to the database after detaching it
//    //    check this chat with chatGPT about these operations https://chatgpt.com/share/67361537-168c-8013-9d7c-aca81566050d
//
//
////    associating/linking two entities of the related entity classes: --- check the section "persistence/flushing/saving of an entity into the database" below ---
//    // ⁃ for persisting/saving entities:
//    //   before persisting, we must set the relationship data field in the entity/object instance of the owner side entity class
//    //   to refer/point to the entity/object instance of the related entity class.
//    // ⁃ for setting the data fields of java object instances, when managed by spring data jpa:
//    //   we have to set the relationship data field in the entity/object instance of the owner side entity class
//    //   to refer/point to the entity/object instance of the related entity class. and we have to also to
//    //   set the relationship data field in the related entity class to refer/point to the entity/object instance of the owner entity class.
//
//
////    stages of the lifecycle of an entity:
//    // ⁃ transient state: creation of the entity/java object instance of the entity class
//    // ⁃ linking this entity/java object instance of the entity class to the entity/java object instance of its related entity class
//    //   look at the section "associating/linking two entities of the related entity classes"
//    // ⁃ managed state: persisting this entity/java object instance of the entity class. in this stage, the entity becomes managed by spring data jpa,
//    //   and any change to it will then in the next stage be persisted/saved to the database
//    // ⁃ commiting of the transaction or flushing the changes to the database. this will save the changes to the database
//    // - detached state: when the entity is detached from the database context,
//    //   and hence any changes to this entity won't be persisted to the database - when commiting the transaction or flushing the changes - unless
//    //   the entity is merged back into the database context
//    // - merged state: when the entity is merged back into the database context
//    // - removed state: when the entity is removed from the database context. this means the record of this
//    //   entity is marked for deletion in the database during the next commiting of the transaction or flushing the changes to the database
//
//
////    persistence/flushing/saving of an entity into the database:
//    // ⁃ if the entity to be persisted is the owner side:
//    //   then persisting this entity will also persist the entity of the related entity class.
//    //   but obviously before that, we must set the relationship data field in this owner side entity to refer/point to the entity/object instance of the related entity class.
//    // ⁃ if the entity to be persisted is the inverse side:
//    //   - then if the cascade attribute is set to persist/ALL:
//    //     then persisting this entity will also persist the entity of the related entity class.
//    //     and obviously after setting the relationship data field in the owner side entity to refer/point to the entity/object instance of the related entity class.
//    //   - else if the cascade attribute is set to nothing:
//    //     then persisting this entity won't also persist the entity of the related entity class. and hence in this case,
//    //     we need to manually persist the entity/object instance of the inverse side entity class
//    //     and persist the entity/object instance of the related entity class, which is the owner side entity class.
//    //     and obviously after setting the relationship data field in the owner side entity to refer/point to the entity/object instance of the related entity class.
//
//
//
////    uni-directional vs bidirectional relationships between entity classes:
//    // ⁃ in bidirectional:
//    //   - setting the values of the relationship data fields in the related entity classes:
//    //     - we need to set the value of the @JoinColumn annotation (for two reasons: to persist the related entities to the database,
//    //       and to set the value of the data field of the java object instance - of the entity class containing this data field annotated with @JoinColumn annotation - in the in-memory of the backend server because the data fields annotated with relationship annotations like @ManyToOne, @OneToOne, ...
//    //       are managed by spring data jpa, and hence unless annotated with relationship annotations,
//    //       spring data jpa won't manage these annotated data fields and will ignore setting their values unlike data fields not annotated with relationship annotations.like @ManyToOne, @OneToOne, ...).
//    //     - we need also to set the value of the other relationship data field that is present in the other related entity class.
//    //       where this data field is annotated with the other relationship annotation, like @OneToMany, @OneToOne, ...
//    //   - navigating/getting/retrieving the entity/object instance of the entity class from its related entity class:
//    //     - we can use the getter method to get the relationship data field of type B of entity/object instance of the entity class A. where class A and class B are the related entity classes
//    //     - we can use the getter method to get the relationship data field of type A of entity/object instance of the entity class B. where class A and class B are the related entity classes
//    // ⁃ in uni-directional:
//    //   - setting he values of the relationship data fields in the related entity classes:
//    //     - we need only to set the value of the @JoinColumn annotation to persist the related entities to the database.
//    //   - navigating/getting/retrieving the entity/object instance of the entity class from its related entity class:
//    //     - we can use the getter method to get the relationship data field of type B of entity/object instance of the entity class A, where this data field is the annotated data field with the relationship annotation like @ManyToOne, @OneToOne, ... in this relationship.
//    //     - and we can't use the getter method to get the other relationship data field that is not annotated with the relationship annotation like @ManyToOne, @OneToOne, ... in this relationship
//
//
//
////    miscellaneous notes I wrote in my iphone: --- these notes could be repetition of the notes above ---
//    // ⁃ Entity class represents a table, entity represents a record in this table
//    // ⁃ @JoinColumns determines the owning side of the relationship and determines the foreign key of the relationship
//    // ⁃ Uni-directional mapping saves up memory by not having two references for the entities/object instances of
//    //   related entity classes A and B. That is not to do both together, not to set the data field B in class A to refer/point to an
//    //   entity/object instance of class B. And not to set the data field A in class B to
//    //   refer/point to an entity/object instance of class A. Where of course class A and B must be related entities.
//    // ⁃ Navigating in uni-directional vs bidirectional: navigating means to access/get/retrieve the
//    //   value of the data field that is annotated with relationship annotations (@OneToMany, @ManyToOne, @ManyToMany, @OneToOne)
//    // ⁃ Owning side (the class that contains the data field annotated with @JoinColumn) is the only side that is
//    //   responsible for updating the foreign key column in the database.
//    // ⁃ @JoinColumn data field "b" (of type "B") of an entity/object instance of an entity class "A" must be
//    //   set to an entity/object instance of the related entity class "B". This value of this @JoinColumn data field will
//    //   be taken by JPA during persistence
//    //   stage (persistence of the data of an entity/object instance from the in-memory backend
//    //   server (that is as java object instance in the memory) into the database (that is as record in the table)) to create a
//    //   reference link (foreign key referencing) between the tables of the related entity classes A and B. Otherwise, the value of
//    //   the foreign key specified inside this annotation @JoinColumn will have
//    //   null value in record of the table in the database. Note that setting the data field a (of type A) of an entity/object
//    //   instance of an entity class B to refer/point to an entity/object instance of the related entity class A will not be
//    //   taken by JPA when persisting these two entities into the database, because this data field a
//    //   is not annotated with @JoinColumn
//    // ⁃ Bi-directional mapping is only about having the ability to get/access
//    //   BOTH (and not only one, that is the one annotated with relationship annotation; Where this is
//    //   the case of unidirectional mapping) relationship data fields of both related entity classes.
//    // ⁃ Relationship data fields are data fields that are under JPA’s relationship management, and they are
//    //   not like any other java data field. JPA knows that the owning side has a relationship data field, but it
//    //   doesn't know about the existence of the relationship data field in the inverse side unless this data field
//    //   was annotated with relationship annotation (@OneToMany, …).
//    //   In other words, JPA knows that an object instance of class A (owning side, that is that
//    //   contains @JoinColumn) has related object instance(s) of class B, but it doesn’t know
//    //   that an object instance of class B (inverse side, that is that does not contain @JoinColumn) has
//    //   related object instance(s) of class A unless the data field if class A inside class B is
//    //   annotated with relationship annotation, because it is not JPA’s duty to keep track of the
//    //   entity(s)/object instance(s) of the relationship data fields of both entity classes of the
//    //   same relationship, it’s duty is to only keep track of just one of these data fields only, in
//    //   order to associate/link the related/associated/referencing records of these
//    //   related entity classes together in the database.
//    // ⁃ Choosing which entity class is the Owning side and inverse side doesn't change the schema
//    //   in the database, that is where the foreign key is in a relationship, …
//    // ⁃ Persisting one of entities of the relationship or both of them is depended on whether
//    //   the persisted entity is the owner side or not; then, in case of this entity being the inverse side, then
//    //   the cascade property will determine whether we should persist both entities of the relationship or just one of them
//    // ⁃ Java object graph
//
////    ------------------------------------------------------------
//
//
//
//    //    the no-args constructor should always be defined in entity classes...
//
//
//    //    - a constructor with a certain number of args could be defined in
//    //    entity classes, for example, when we want to configure the object instance of this entity class to be initialized with
//    //    certain values for its data fields. like for example, to set the initial state of the Order entity class...
//
//
//
////    the below data fields must all be classes/enums/records. and they can be primitive data types as well
//
//    @Id
//    @GeneratedValue(
//            strategy = GenerationType.AUTO
//                //            the above line means spring will choose the best strategy (whether sequence or table or ...) for the database we have(Postgres, mySQL, ...)
//                //    or
//                //    strategy = GenerationType.IDENTITY
//    )
//        //    or
//        //    @SequenceGenerator(
//        //            name = "name1",
//        //            sequenceName = "name1",
//        //            allocationSize = 1
//        //    )
//        //    @GeneratedValue(
//        //            strategy = GenerationType.SEQUENCE,
//        //            generator = "name1"
//        //    )
//    private Long id;
//        //    remove the above data field if the entity class/table is annotated with @Embeddable, that is, if the entity class/table is a weak entity class
//
//
//    @Enumerated(EnumType.STRING)
//    private ClassName1 ObjectName1;
//        //    you should beside that create an enum class called ClassName1
//
//
//    @Column(
//            name = "columnName1",
//            unique = true,
//            nullable = false,
//            insertable = false,
//            updatable = false,
//            columnDefinition = "VARCHAR(60)",
//            table = "nameOfTheTable1",
//            length = maximumNbrOfCharacters1,
//                //    the above annotation means the "objectName2" attribute must have a MAXIMUM of 60 characters
//            precision = 60,
//            scale = 2,
////            -----------------
//            value = value1
//    )
//    private String ObjectName2;
//
//
//    private CLassName2 objectName3 = value1;
//        //    the above is the default value of the attribute objectName3
//
//
//
////  - you can have an @Embedded data field in an @Embeddable class
////  - a new separate dedicated table of the below data field won't be
////    created. the row of the below data field will be stored in the same
////    table as this entity class. thus no need to JOIN operations to apply SQL
////    queries on this below data field
//    @Embedded
//        // the above annotation was added here because the embeddable class of this data field is a single data type and not a collection
//        // this data field being annotated with this annotation means that the class of this field is a weak entity class;
//        // where there is no primary key for the class of this field as identifying this class is not important or needed.
//        // in this case, the class of this field is typically not having a many-to-many or one-to-many relationships with other entity classes
//    private WeakEntityCLassName5 objectName6;
//        //    if an embedded class has a one-to-many relationship with a table, then it is wrong to consider it as embedded/weak class
//        //    an embedded class can only have one-to-one or many-to-one relationship with a table
//        //    an embedded class can have these one-to-one or many-to-one relationships with more than one table
//        //    making a class embedded rather than an entity class will make the data of this class to be redundant for many records of the table this embedded class is related to. so, this embedded class better be having small amount of data or attributes
//        //    making a class embedded (or more specifically, when using @ElementCollection) rather than an entity class will make the SQL queries to be less efficient and take more time, because the embedded does not actually contain a foreign key,and hence no indexing advantage
//        //    making a class embedded rather than an entity class will restrict the scaling options of this embedded class. for example, this embedded class can't be made to have one-to-many or many-to-many relationships with tables
//        //    think about embedding only when the class-to-be-embedded has either one-to-many or one-to-one relationship with the table where this to-be-embedded-class is embedded in
//        //    making a class embedded rather than an entity class will prevent us from applying SQL queries on this embedded table. SQL queries such as selecting, adding, deleting records from this table. SQL queries also such as aggregates (sum, ...). SQL queries also like joining this table with other tables. ...
//
//
//
//
////  - you can have an @ElementCollection data field in an @Embeddable class
////  - a table will be created for the below data field, this table contains foreign
////    key only and doesn't contain a primary key
////  - unlike one-to-many relationship, here we dont have to set the data field of @Embeddable class
////    to point to the object instance of the related entity class EntityClass1 (this class) that
////    contains the below data field.
////  - so, we need to do a JOIN operation to apply SQL queries on this below data field
//    @ElementCollection(
//            fetch = FetchType.EAGER
//            //            this means when this table is fetched from the database,
//            //            the related table representing the below data field "objectName6" will also be fetched from the database along with it
//    )
//    @CollectionTable(
//            name = "nameOfTheEmbeddableTable1",
//            joinColumns = @JoinColumn(
//                    name = "theNameOfTheForeignKeyOfTheTableOfTheBelowEmbeddedDataField1"
//                    //    note that the foreign key in the embedded class is not actually a foreign key
//            )
//    )
////    the above two annotations are used for when the embeddable class of the below data field is a collection
//    private WeakEntityCLassNameCollection5 objectName7;
//    //    if an embedded class has a one-to-many relationship with a table, then it is wrong to consider it as embedded/weak class
//    //    an embedded class can only have one-to-one or many-to-one relationship with a table
//    //    an embedded class can have these one-to-one or many-to-one relationships with more than one table
//    //    making a class embedded rather than an entity class will make the data of this class to be redundant for many records of the table this embedded class is related to. so, this embedded class better be having small amount of data or attributes
//    //    making a class embedded (or more specifically, when using @ElementCollection) rather than an entity class will make the SQL queries to be less efficient and take more time, because the embedded does not actually contain a foreign key,and hence no indexing advantage
//    //    making a class embedded rather than an entity class will restrict the scaling options of this embedded class. for example, this embedded class can't be made to have one-to-many or many-to-many relationships with tables
//    //    think about embedding only when the class-to-be-embedded has either one-to-many or one-to-one relationship with the table where this to-be-embedded-class is embedded in
//    //    making a class embedded rather than an entity class will prevent us from applying SQL queries on this embedded table. SQL queries such as selecting, adding, deleting records from this table. SQL queries also such as aggregates (sum, ...). SQL queries also like joining this table with other tables. ...
//
//
//
//
//
//
//    @JsonProperty(
//            access = JsonProperty.Access.READ_ONLY
//                //            this means, the value of the below data field is set inside the backend server by us developers
//                //            and not by the value - of the key associated with the below data field - that is sent in the JSON request
//    )
//    private ClassName8 objectInstance10;
//
//
//
//    @Convert(
//            converter = CustomConverter1.class
//    )
//    private ClassName9 objectInstance11;
//
//
//
//    @JsonIgnore
//        //    the above annotation will prevent the serialization (and also deserialization) of this field. which means that this field will not be shown in the JSON response.
//        //    @JsonIgnore annotation is designed to ignore specific fields during JSON serialization (and also deserialization).
//
//    @ManyToOne
//        //    - in the case of unidirectional relationship/mapping, if this annotation was added here, then the annotation @OneToMany should not be added in the class "ClassName3"
//        //    - in the case of unidirectional relationship/mapping, using @Many-to-Many annotation is better than using @One-To-Many annotation when it comes to saving up memory by reducing the number of references/pointers.
//        //    - in case of bidirectional relationship/mapping, both annotations @ManyToOne and @OneToMany could/should be added in both entity classes of this relationship
//        //    - related entities persistence: if the sent json request contains records/rows of this table that contains the foreign key and also contains (as nested JSON sub-object) the record of the table that doesn't contain the foreign key in this relationship,
//        //      that is, the value of the primary key of this latter record/row was not created/persisted before,
//        //      then the result will be the creation of two java object instances, one for each entity class of this relationship.
//        //      but these two java object instances won't be associated with each other;
//        //      unless we manually set the data field annotated with @JoinColumn in an entity/object instance that contains it to refer/point to the entity/object instance of the other related entity class in this relationship.
//        //      then we later persist/commit/flush these two entities from the in-memory (java object instances) of the backend server to the database
//        //    - JPA's relationship management: in unidirectional relationship/mapping, we only need to set the value of the data field annotated with @JoinColumn in the entity/object instance that contains it
//        //      while in bidirectional relationship/mapping, we need to also set the value of the relationship data field of the entity/object instance of the other related entity class (that does not contain the data field annotated with @JoinColumn) in this relationship
//        //      note that the above note is related to JPA's relationship management, and it is not related to JPA's mechanism of persisting/commiting/flushing the data into the database
//            (
//            targetEntity = ClassName3.class,
//                //    note sure about the value of the above property
//            cascade = CascadeType.ALL,
//                //    the above means that the changes to this table will cascade to the table of the below data field.
//                //    the changes could be operations like persist, merge, remove, refresh, and detach
//                //    - the above means that if the entity/object instance of this entity class is to be persisted/updated/removed/refreshed/detached/merged,
//                //      then we can just persist/update/remove/refresh/detach/merge it without the need to manually persist/update/remove/refresh/detach/merge the entity/object instance of the below data field
//                //    the default value of the "cascade" attribute is to not cascade any changes on this table to the table of the below data field
//            fetch = FetchType.LAZY,
//                    //    - this means when this table is fetched from the database,
//                    //      the related table representing the below data field "objectName4" will not be fetched from the database along with it
//            optional = false
//
//        //    the attribute "mappedBy" is not present in @ManyToOne annotation because @ManyToOne annotation is always present in the owning side of the relationship
//    )
//    @JoinColumn(
//            name = "nameOfTheForeignKey1",
//            referencedColumnName = "primaryKeyOfTheTableThatDoesn'tHaveTheForeignKey1",
//            unique = true,
//            nullable = false,
//            insertable = false,
//            updatable = false,
//            columnDefinition = "VARCHAR(255) NOT NULL",
//                //    note sure about the value of the above property
//            table = "tableThatDoesn'tHaveTheForeignKey1",
//                //    note sure about the value of the above property
//            foreignKey = @ForeignKey(
//                    name = "nameOfTheForeignKey1"
//                //    note sure about the value of the above property
//            )
//
//    )
//        //    in case of bidirectional relationship/mapping, only one of the data fields annotated with @ManyToOne or @OneToMany should have the @JoinColumn annotation. typically the data field annotated with @ManyToOne
//    private ClassName3 ObjectName4;
//        //    remove the above data field if the entity class/table is annotated with @Embeddable, that is, if the entity class/table is a weak entity class
//
//
//
//    @JsonIgnore
//        //    the above annotation will prevent the serialization (and also deserialization) of this field. which means that this field will not be shown in the JSON response.
//        //    @JsonIgnore annotation is designed to ignore specific fields during JSON serialization (and also deserialization).
//
//    @OneToMany
//        //    - in the case of unidirectional relationship/mapping, if this annotation was added here, then the annotation @ManyToOne should not be added in the class "CollectionClassName4"
//        //    - in the case of unidirectional relationship/mapping, using @Many-to-Many annotation is better than using @One-To-Many annotation when it comes to saving up memory by reducing the number of references/pointers.
//        //    - in case of bidirectional relationship/mapping, both annotations @ManyToOne and @OneToMany could/should be added in both entity classes of this relationship
//        //    - related entities persistence: if the sent json request contains records/rows of this table that contains the foreign key and also contains (as nested JSON sub-object) the record of the table that doesn't contain the foreign key in this relationship,
//        //      that is, the value of the primary key of this latter record/row was not created/persisted before,
//        //      then the result will be the creation of two java object instances, one for each entity class of this relationship.
//        //      but these two java object instances won't be associated with each other;
//        //      unless we manually set the data field annotated with @JoinColumn in an entity/object instance that contains it to refer/point to the entity/object instance of the other related entity class in this relationship.
//        //      then we later persist/commit/flush these two entities from the in-memory (java object instances) of the backend server to the database
//        //    - JPA's relationship management: in unidirectional relationship/mapping, we only need to set the value of the data field annotated with @JoinColumn in the entity/object instance that contains it
//        //      while in bidirectional relationship/mapping, we need to also set the value of the relationship data field of the entity/object instance of the other related entity class (that does not contain the data field annotated with @JoinColumn) in this relationship
//        //      note that the above note is related to JPA's relationship management, and it is not related to JPA's mechanism of persisting/commiting/flushing the data into the database
//        //    - note: this annotation must be added beside adding the annotation @ManyToOne, because the data field that has type Collection must be
//        //      annotated with the @OneToMany (or another suitable annotation). or we can choose to just add the data fields
//        //      with annotation @ManyToOne ( or @OneToOne or @ManyToMany) on the first side of the relationship and
//        //      choose not to add the data fields annotated with the complementary annotations (@OneToMany or @OneToOne or @ManyToMany) at the other side of the relationship
//            (
//            mappedBy = "nameOfTheForeignKey2",
//                //   - without "mappedBy" property, spring data jpa will treat the below data field as
//                //     a separate relationship than the relationship of the related data field in the
//                //     related entity class, and thus will create a relationship/join table for this
//                //     one-to-many relationship
//            cascade = CascadeType.ALL,
//                //    the above means that the changes to this table will cascade to the table of the below data field.
//                //    the changes could be operations like persist, merge, remove, refresh, and detach
//                //    - the above means that if the entity/object instance of this entity class is to be persisted/updated/removed/refreshed/detached/merged,
//                //      then we can just persist/update/remove/refresh/detach/merge it without the need to manually persist/update/remove/refresh/detach/merge the entity/object instance of the below data field
//                //    the default value of the "cascade" attribute is to not cascade any changes on this table to the table of the below data field
//            fetch = FetchType.LAZY,
//                //    - this means when this table is fetched from the database,
//                //      the related table representing the below data field "objectName5" will not be fetched from the database along with it
//            orphanRemoval = true
//                //            the above means that when an object instance inside the below data field is removed from this data field (that is removed from the collection);
//                //            then the record of this object instance will be removed from the table of the below data field, in other words removed from the database.
//                //            this maintains referential integrity
//
//    )
//        //    - the property "mappedBy" in the above line tells spring data JPA that the foreign key of this relationship is present in this table "User" and its name is "nameOfTheForeignKey2" (in this entity class "User").
//        //      without this property, spring data jpa will create a relationship table for this one-to-many relationship, which is a wrong thing to be done.
//        //    - the entity class that does not contain the annotation @JoinColumn in a relationship is called the inverse side of the relationship
//        //    - the annotation @JoinColumn is not present in @OneToMany annotation because @OneToMany annotation is always present in the inverse side of the relationship.
//        //    - in the case of bidirectional relationship/mapping, only one of the data fields annotated with @ManyToOne or @OneToMany should have the @JoinColumn annotation. and this data field annotated is the one that is annotated with @ManyToOne
//    private CollectionClassName4 ObjectName5;
//        //    remove the above data field if the entity class/table is annotated with @Embeddable, that is, if the entity class/table is a weak entity class
//
//
//
//
//    @JsonIgnore
//        //    the above annotation will prevent the serialization (and also deserialization) of this field. which means that this field will not be shown in the JSON response.
//        //    @JsonIgnore annotation is designed to ignore specific fields during JSON serialization (and also deserialization).
//
//    @ManyToMany
//            //    - in the case of unidirectional relationship/mapping, if this annotation was added here, then the annotation @ManyToMany should not be added in the class "CollectionClassName7"
//            //    - in case of bidirectional relationship/mapping, annotation @ManyToMany could/should be added in each entity classes of this relationship
//            //    - related entities persistence: if the sent json request contains records/rows of this table that contains the foreign key and also contains (as nested JSON sub-object) the record of the table that doesn't contain the foreign key in this relationship,
//            //      that is, the value of the primary key of this latter record/row was not created/persisted before,
//            //      then the result will be the creation of two java object instances, one for each entity class of this relationship.
//            //      but these two java object instances won't be associated with each other;
//            //      unless we manually set the data field annotated with @JoinColumn in an entity/object instance that contains it to refer/point to the entity/object instance of the other related entity class in this relationship.
//            //      then we later persist/commit/flush these two entities from the in-memory (java object instances) of the backend server to the database
//            //    - JPA's relationship management: in unidirectional relationship/mapping, we only need to set the value of the data field annotated with @JoinColumn in the entity/object instance that contains it
//            //      while in bidirectional relationship/mapping, we need to also set the value of the relationship data field of the entity/object instance of the other related entity class (that does not contain the data field annotated with @JoinColumn) in this relationship
//            //      note that the above note is related to JPA's relationship management, and it is not related to JPA's mechanism of persisting/commiting/flushing the data into the database
//            //    - note: we can choose to just add the data field with annotation @ManyToMany on the first side of the relationship and
//            //      choose not to add the data field annotated with the complementary annotation @ManyToMany at the other side of the relationship
//            (
//            targetEntity = CollectionClassName3.class,
//                //    note sure about the value of the above property
//            cascade = CascadeType.ALL,
//                //    the above means that the changes to this table will cascade to the table of the below data field.
//                //    the changes could be operations like persist, merge, remove, refresh, and detach
//                //    - the above means that if the entity/object instance of this entity class is to be persisted/updated/removed/refreshed/detached/merged,
//                //      then we can just persist/update/remove/refresh/detach/merge it without the need to manually persist/update/remove/refresh/detach/merge the entity/object instance of the below data field
//                //    the default value of the "cascade" attribute is to not cascade any changes on this table to the table of the below data field
//            fetch = FetchType.LAZY,
//                //    - this means when this table is fetched from the database,
//                //      the related table representing the below data field "objectName7" will not be fetched from the database along with it
//            mappedBy = "nameOfThePrimaryKeyOfTheOtherTableInThisRelationship1"
//                //    note sure about the value of the above property
//                //    the attribute "mappedBy" is always present in the inverse side of the relationship
//    )
//
//    @JoinTable(
//            name = "",
//            catalog = "",
//            schema = "",
//            joinColumns = @JoinColumn(
//                    name = "joinColumnName1",
//                    referencedColumnName = "primaryKeyOfTheFirstTable1"
//                        //                    primary key of the first table in this relationship
//            ),
//                //    in the case of bidirectional relationship/mapping, only one of the data fields annotated with @ManyToMany should have the @JoinColumn annotation inside the @JoinTable. and the other data field annotated with @ManyToMany in the other related entity class should have the attribute "mappedBy".
//            inverseJoinColumns = @JoinColumn(
//                    name = "inverseJoinColumnName2",
//                    referencedColumnName = "primaryKeyOfTheSecondTable2"
//                        //                    primary key of the other table in the relationship
//            ),
//                //            inverseJoinColumns represent the primary key of the other table in the relationship other than the first table
//            foreignKey = @ForeignKey(
//                    name = "",
//                    value = ConstraintMode.NO_CONSTRAINT,
//                        //    note sure about the value of the above property
//                    foreignKeyDefinition = ""
//                        //    note sure about the value of the above property
//            )
//    )
//        //    this @JoinTable is to create a relationship table
//    private CollectionClassName5 objectName7;
//        //    this is the table of the relationship
//
//
//
//    @JsonIgnore
//        //    the above annotation will prevent the serialization (and also deserialization) of this field. which means that this field will not be shown in the JSON response.
//        //    @JsonIgnore annotation is designed to ignore specific fields during JSON serialization (and also deserialization).
//
//    @OneToOne
//        //    - in the case of unidirectional relationship/mapping, if this annotation was added here, then the annotation @OneToOne should not be added in the class "ClassName6"
//        //    - in case of bidirectional relationship/mapping, annotation @OneToOne could/should be added in each entity classes of this relationship
//        //    - related entities persistence: if the sent json request contains records/rows of this table that contains the foreign key and also contains (as nested JSON sub-object) the record of the table that doesn't contain the foreign key in this relationship,
//        //      that is, the value of the primary key of this latter record/row was not created/persisted before,
//        //      then the result will be the creation of two java object instances, one for each entity class of this relationship.
//        //      but these two java object instances won't be associated with each other;
//        //      unless we manually set the data field annotated with @JoinColumn in an entity/object instance that contains it to refer/point to the entity/object instance of the other related entity class in this relationship.
//        //      then we later persist/commit/flush these two entities from the in-memory (java object instances) of the backend server to the database
//        //    - JPA's relationship management: in unidirectional relationship/mapping, we only need to set the value of the data field annotated with @JoinColumn in the entity/object instance that contains it
//        //      while in bidirectional relationship/mapping, we need to also set the value of the relationship data field of the entity/object instance of the other related entity class (that does not contain the data field annotated with @JoinColumn) in this relationship
//        //      note that the above note is related to JPA's relationship management, and it is not related to JPA's mechanism of persisting/commiting/flushing the data into the database
//        //    - note: we can choose to just add the data field with annotation @OneToOne on the first side of the relationship and
//        //      choose not to add the data field annotated with the complementary annotation @OneToOne at the other side of the relationship
//            (
//            targetEntity = ClassName6.class,
//                //    note sure about the value of the above property
//            cascade = CascadeType.ALL,
//                //    the above means that the changes to this table will cascade to the table of the below data field.
//                //    the changes could be operations like persist, merge, remove, refresh, and detach
//                //    - the above means that if the entity/object instance of this entity class is to be persisted/updated/removed/refreshed/detached/merged,
//                //      then we can just persist/update/remove/refresh/detach/merge it without the need to manually persist/update/remove/refresh/detach/merge the entity/object instance of the below data field
//                //    the default value of the "cascade" attribute is to not cascade any changes on this table to the table of the below data field
//            fetch = FetchType.LAZY,
//                //    - this means when this table is fetched from the database,
//                //      the related table representing the below data field "objectName7" will not be fetched from the database along with it
//            optional = false,
//            mappedBy = "nameOfThePrimaryKeyOfTheOtherTableInThisRelationship1",
//                //    in the case of bidirectional relationship/mapping, only one of the data fields annotated with @OneToOne should have the @JoinColumn annotation. and the other data field annotated with @ManyToMany in the other related entity class should have the attribute "mappedBy".
//                //    the above means that this entity class/table has the primary key of the other table in this relationship as its foreign key in this one-to-one relationship
//            orphanRemoval = true
//                //            the above means that when the object instance of the below data field is set to null;
//                //            then the record of this object instance will be removed from the table of the below data field, in other words, removed from the database.
//                //            this maintains referential integrity
//    )
//    @JoinColumn(
//            name = "nameOfTheForeignKey1",
//                //            this is the name of the foreign key in this table
//            referencedColumnName = "primaryKeyOfTheTableThatDoesn'tHaveTheForeignKey1"
//                //            this is the name of the primary key in the other table referenced by the foreign key of this table in this relationship
//    )
//        //    - in one-to-one relationship, in which table to add the foreign key is dependent on many criteria:
//        //      - the table that depends on the other table when it comes to the business logic or
//        //        design is the table that should contain the foreign key. for example, "Profile" table depends on
//        //        the "User" table, and hence the "Profile" table should contain the foreign key.
//        //      - the table that ALL of its rows are attached/linked/associated/related to rows in
//        //        the other table is the table that should contain the foreign key. for example, all rows in
//        //        "CartItem" table are attached/linked/associated/related to
//        //        rows in the "Product" table, and hence the "CartItem" table should contain the foreign key.
//        //      - the foreign key must go to the smaller table (lower number of rows), where lower number of rows means better
//        //        indexing and faster performance
//        //      - the table with more flexibility and that makes more sense for querying is the table that should contain
//        //        the foreign key. for example, if table A rows will always query and need table B rows, then placing the foreign key in
//        //        table A makes the database design simpler.
//        //      - the owner table is the table that must contain the primary key of the other table in this relationship.
//        //      - sometimes the foreign key can be nullable, where the rows with null foreign key are rows that have no
//        //        relationship with rows of the other table in this relationship.
//        //      - if the relationship/mapping is bidirectional, then it doesn't matter which table contains the foreign key.
//
//        //    - in the case of bidirectional relationship/mapping, only one of the data fields annotated with @OneToOne should have the @JoinColumn annotation. and the other data field annotated with @ManyToMany in the other related entity class should have the attribute "mappedBy".
//        //    - the @JoinColumn annotation here means that this table in this relationship is the table that has the primary key of the other table in this relationship as its foreign key
//    private ClassName6 objectName8;
//
//
//
//
//
//
//
////    Helper methods... (doing database operations that is related to the data fields/properties of this entity class).
////    - like when this entity class has Collection data fields, then
////      the helper methods in this case might be adding an element to
////      this array, removing an element from this array, etc.
////    - or like when there is a state of this entity class (Order state for example), then
////      the helper methods might be changing the state of this entity class.
////    - [][][] check the last message in this chat with
////      ChatGPT: https://chatgpt.com/share/6733d54f-47e8-8013-a025-1e4cdc539e3f
////    - Helper methods are methods that are tightly related to the data fields/properties of
////      this entity class.
////    - for example, the methods that apply certain condition or checking on a
////      certain data field of an entity/object instance of this entity class. like having certain value for the Role data
////      field, or whether the "balance" data field is greater than 0 or not, etc. also, the methods that
////      apply certain condition or checking on a certain data field(s) of an entity/object
////      instance of this entity class compared to another entity/object instance of this entity class.
////      for example, comparing the values (in Reservation table in the database) of "startDate" and "endDate" of a certain
////      entity/object instance/record of reservation entity class and another entity/object instance of
////      reservation entity class, in order to check whether these two reservations overlap or not.
////    - or, the methods that apply certain operations on the elements of a Collection data
////      field in this entity class. such as calculating the total price of
////      the order items in the order, etc.
////    - check this chat with ChatGPT about helper methods
////      in entity classes: https://chatgpt.com/share/6734c4dd-d338-8013-a29c-b86164fa3ac4
//
//    //    the below method customization is not added to the @Embeddable class
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        TemplateEntityClass that = (TemplateEntityClass) o;
//        return id.equals(that.id);
//        //  //  or
//        //  return Objects.equals(id, that.id);
//    }
//    //  - the above is to override the equals method to state that two categories with the same
//    //    id are equal. this is a good practice for entity classes in JPA. and since we overrode
//    //    equals() method, we should also override hashCode() method.
//
//    //    the below method customization is not added to the @Embeddable class
//    public int hashCode() {
//        return Objects.hash(id);
//    }
//    //    the above means the hashing of this entity class will be based on the id of this
//    //    entity class instead of the object reference. so, in a HashMap or a HashSet, two
//    //    TemplateEntityClass having the same id will be considered equal. and hence in the case of
//    //    HashSet, it will only keep one of them when adding the two of them.
//
//
//}
