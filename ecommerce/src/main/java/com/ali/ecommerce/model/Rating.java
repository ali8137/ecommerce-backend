package com.ali.ecommerce.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rating {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @Enumerated(EnumType.STRING)
    private RatingValue ratingValue;
    @ManyToOne
    @JoinColumn(
            referencedColumnName = "id"
    )
    private User user;
    @ManyToOne
    @JoinColumn(
            referencedColumnName = "id"
    )
    private Product product;
    @ManyToOne
    @JoinColumn(
            referencedColumnName = "id"
    )
    private Review review;


//    Helper methods

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Rating that = (Rating) o;
        return Objects.equals(this.id, that.id);
        //  return id.equals(that.id);
    }

    public int hashCode() {
        return Objects.hash(this.id);
    }
}
