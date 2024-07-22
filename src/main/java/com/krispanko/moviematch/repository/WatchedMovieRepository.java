package com.krispanko.moviematch.repository;

import com.krispanko.moviematch.model.WatchedMovie;
import com.krispanko.moviematch.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface WatchedMovieRepository extends JpaRepository<WatchedMovie, Long> {

    /**
     * Custom query to find a watched movie by user and movie ID.
     *
     * @param user The user who watched the movie.
     * @param movieId The ID of the movie.
     * @return An optional containing the watched movie if found.
     */
    Optional<WatchedMovie> findByUserAndMovieId(User user, Long movieId);

    /**
     * Custom query to find all watched movies by a user.
     *
     * @param user The user who watched the movies.
     * @return A list of watched movies.
     */
    List<WatchedMovie> findByUser(User user);
}
