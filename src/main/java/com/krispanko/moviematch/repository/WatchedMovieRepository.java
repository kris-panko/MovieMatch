package com.krispanko.moviematch.repository;

import com.krispanko.moviematch.model.WatchedMovie;
import com.krispanko.moviematch.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface WatchedMovieRepository extends JpaRepository<WatchedMovie, Long> {
    Optional<WatchedMovie> findByUserAndMovieId(User user, Long movieId);
    List<WatchedMovie> findByUser(User user);
}
