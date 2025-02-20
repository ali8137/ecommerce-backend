package com.ali.ecommerce.model;

import lombok.Getter;

@Getter
public enum RatingValue {
    ONE(1),
    ONE_HALF(1.5),
    TWO(2),
    TWO_HALF(2.5),
    THREE(3),
    THREE_HALF(3.5),
    FOUR(4),
    FOUR_HALF(4.5),
    FIVE(5);
    //    the above are constructors

    private final double value;

    RatingValue(double value) {
        this.value = value;
    }

}
