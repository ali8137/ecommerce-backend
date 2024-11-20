package com.ali.ecommerce.model;


import jakarta.persistence.Embeddable;

@Embeddable
public class Address {

    private String street;
    private String city;
    private String ZipCode;
}
