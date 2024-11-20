package com.ali.ecommerce.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Data
    // this is an annotation from "lombok", it provides the getters and setters of the class "Student". it also provides toString(), hashCode() and equals() of this class
@NoArgsConstructor
@AllArgsConstructor
@Builder
    // the above is optional. it is just for better code readability
public class User {

//    spring data jpa deserialization of the JSON request:
    //    when a JSON request that contains a JSON object that corresponds to this entity class/table, and inside this JSON object,
    //    there are nested JSON objects that correspond to the entity classes/tables that are related to this entity class/table, is sent to a REST API;
    //    in this case, each JSON object will be converted/deserialized to the corresponding Java object.
    //    however, in the database, these resulted nested java objects won't be associated with the resulted java object of this entity class/table.
    //    to associate them with the resulted java object of this entity class/table,
    //    we should manually set these nested java objects to be associated with the resulted java object of this entity class/table.


    //    the no-args constructor should always be defined in entity classes

//    public User() {
//        this.createdAT = LocalDateTime.now();
//    }
    //    the above data fields values are better to be set in the service layer rather than the constructor above,
    //    this is for better separation of concerns/layers

    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private Long id;
    private String firstName;
    private String lastName;
    @Column(
            length = 60
    )
    private String password;
    @ElementCollection
    @CollectionTable(
            joinColumns = @JoinColumn(
                    name = "user_id"
            )
    )
    @Embedded
        // this data field being annotated with this annotation means that the class of this field is a weak entity class;
        // where there is no primary key for the class of this field as identifying this class is not important or needed.
        // in this case, the class of this field is typically not having a many-to-many or one-to-many relationships with other entity classes
    private List<Address> addresses;
    private String phoneNumber;
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;
    private LocalDateTime createdAT;
    private List<Rating> ratings;
    private List<Review> reviews;



    //    helper methods:

    public void addAddress(Address address) {
        this.addresses.add(address);
    }

    public void addRating(Rating rating) {
        this.ratings.add(rating);
    }

    public void addReview(Review review) {
        this.reviews.add(review);
    }

}
