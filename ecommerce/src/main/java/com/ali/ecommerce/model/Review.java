package com.ali.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review
{

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private String reviewContent;
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
    @JsonIgnore
    @OneToMany(
            mappedBy = "review",
            cascade = CascadeType.ALL
    )
    private List<Rating> reviewRatings;



//    Helper methods

    public void addRating(Rating rating) {
        this.reviewRatings.add(rating);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Review that = (Review) o;
        return Objects.equals(this.id, that.id);
        //  return id.equals(that.id);
    }

    public int hashCode() {
        return Objects.hash(this.id);
    }
}

