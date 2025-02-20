package com.ali.ecommerce.model;


import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Setter
@Getter
public class Image {

        //    weak entity => no primary key, just foreign key
        private String src;
        private String alt;

}
