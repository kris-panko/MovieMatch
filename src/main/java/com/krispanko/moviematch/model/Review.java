package com.krispanko.moviematch.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long movieId;  // Add this field
    private String reviewText;
    private int rating;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
