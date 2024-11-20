package com.ali.ecommerce.model;


import jakarta.persistence.Embeddable;

@Embeddable
// the values of the data fields of the record are only set using new-instantiation constructor
// the record has only build-in getters
public record Image(String src, String alt) {
}
