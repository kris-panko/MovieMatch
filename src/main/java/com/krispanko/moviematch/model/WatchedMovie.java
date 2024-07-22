package com.krispanko.moviematch.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class WatchedMovie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long movieId;
    private int rating;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public WatchedMovie(Long movieId, User user) {
        this.movieId = movieId;
        this.user = user;
    }
}
