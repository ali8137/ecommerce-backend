package com.ali.ecommerce.model;


import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

//@Embeddable
//// the values of the data fields of the record are only set using new-instantiation constructor
//// the record has only build-in getters
//public record Image(String src, String alt) {
//}
//// it is not a good idea to have table/entity class as a record rather than a class.
//// because the record is immutable by nature and has no non-argument constructor


@Embeddable
@Setter
@Getter
public class Image {

        //    weak entity => no primary key, just foreign key
        private String src;
        private String alt;

}
