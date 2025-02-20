package com.ali.ecommerce.model;


import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class QtyPerSizeAndColor {

    //    design version 1:
    // each CartItem, OrderItem or Product has a list of QtyPerSizeAndColor
    /* TODO: developer-constraint: each CartItem, OrderItem or Product has a list of QtyPerSizeAndColor*/
    @Enumerated(EnumType.STRING)
    private ProductSize productSize;
    @Enumerated(EnumType.STRING)
    private ProductColor productColor;
    private Integer quantity;
    /* TODO: Database design: instead of the above 2, i could have designed a ColorQtyPerSize class and made it to include the above two data fields, and annotate it with @Embeddable. and then added a list of ColorQtyPerSize as a data field instead of the above two data fields, and annotated this list with @ElementCollection and @CollectionTable annotations.*/
    // the above approach is better for design, but may make the SQL queries more complex by introducing of JOIN operations between three tables

    //    design version 2:
    // private Integer smallQty;
    // private Integer mediumQty;
    // private Integer largeQty;
}
