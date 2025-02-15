package com.ali.ecommerce.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;


@Entity
@Data
    // this is an annotation from "lombok", it provides the getters and setters of the class "Student". it also provides toString(), hashCode() and equals() of this class
@NoArgsConstructor
@AllArgsConstructor
@Builder
    // the above is optional. it is just for better code readability
@ToString(
        exclude = {"addresses", "ratings", "reviews"}
)
public class User implements UserDetails
{
//  - "implements UserDetails" was only used to make it easier to implement the method of
//    the UserDetailsService interface with just retrieving the user details from the
//    database as User which is an implementation of UserDetails

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
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @NotBlank(message = "first name should not be empty")
    private String firstName;
    @NotBlank(message = "last name should not be empty")
    private String lastName;
//    we can create a custom validation annotation for password
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
//    @Embedded
    //    the above annotation should not be added here, because the below data field is a collection of data type and not a single data type
        // this data field being annotated with this annotation means that the class of this field is a weak entity class;
        // where there is no primary key for the class of this field as identifying this class is not important or needed.
        // in this case, the class of this field is typically not having a many-to-many or one-to-many relationships with other entity classes
    private List<Address> addresses;
    private String phoneNumber;
    @NotNull(message = "email cannot be null")
    @NotBlank(message = "email cannot be blank")
    @Column(
            unique = true
    )
    /* TODO: developer-constraint: email should be unique */
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(
            updatable = false
    )
    private LocalDateTime createdAT;
    // - we can choose to remove the below @OneToMany annotated data fields in this side of the relationship,
    //   thus having a unidirectional relationship/mapping
    @JsonIgnore
    //    ignore one field when having a bidirectional mapping. better be the @OneToMany field
    @OneToMany(
            mappedBy = "user",
            //   - without "mappedBy" property, spring data jpa will treat the below data field as
            //     a separate relationship than the relationship of the data field "user" in the
            //     entity class "Rating", and thus will create a relationship/join table for this
            //     one-to-many relationship
            cascade = CascadeType.ALL
            //            the above property is usually added for the data field annotated with @OneToMany
    )
    private List<Rating> ratings;
    @JsonIgnore
    //    ignore one field when having a bidirectional mapping. better be the @OneToMany field
    @OneToMany(
            mappedBy = "user",
            //   - without "mappedBy" property, spring data jpa will treat the below data field as
            //     a separate relationship than the relationship of the data field "user" in the
            //     entity class "Review", and thus will create a relationship/join table for this
            //     one-to-many relationship
            cascade = CascadeType.ALL
            //            the above property is usually added for the data field annotated with @OneToMany
    )
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


    //    UserDetails interface methods:
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        User that = (User) o;
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
