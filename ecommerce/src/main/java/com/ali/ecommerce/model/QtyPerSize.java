package com.ali.ecommerce.model;


import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Embeddable
@Getter
public class QtyPerSize {

//    design version 1
    //    private Integer smallQty;
    //    private Integer mediumQty;
    //    private Integer largeQty;

//    design version 2
    @Enumerated(EnumType.STRING)
    private ProductSize productSize;
    private Integer sizeQuantity;
}
