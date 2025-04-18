package com.ali.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private BigDecimal price;
    /* TODO: the above data field better be removed, because there is "price" data field in the related entity class "Product*/
    private String color;
    private String size;
    private Integer quantity;
    @ToString.Exclude
//    @JsonIgnore
    @ManyToOne
    @JoinColumn(
        referencedColumnName = "id"
    )
    private Product product;
    /* TODO: the above relationship reflects certain inconsistency/redundancy in the current design of the database. thus, when changing the database design, it will become consistent, and with no redundancy of data*/
    @ToString.Exclude
    @JsonIgnore
    @ManyToOne
    @JoinColumn(
            referencedColumnName = "id"
    )
    private Order order;


    //    helper methods:
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        OrderItem that = (OrderItem) o;
        return Objects.equals(this.id, that.id);
        //  return id.equals(that.id);
    }

    public int hashCode() {
        return Objects.hash(this.id);
    }
}