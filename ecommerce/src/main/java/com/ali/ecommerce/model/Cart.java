package com.ali.ecommerce.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private BigDecimal totalPrice = BigDecimal.ZERO;
    @JsonIgnore
    @ManyToOne()
    /* TODO: this relationship should have been better defined as one-to-one*/
    @JoinColumn(
            referencedColumnName = "id"
    )
    private User user;
    @OneToMany(
            mappedBy = "cart",
            cascade = CascadeType.ALL
    )
    private List<CartItem> cartItems = new ArrayList<>();

//    helper methods:
    public void addCartItem(CartItem cartItem) {
        this.cartItems.add(cartItem);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Cart that = (Cart) o;
        return Objects.equals(this.id, that.id);
        //  return id.equals(that.id);
    }

    public int hashCode() {
        return Objects.hash(this.id);
    }
}
