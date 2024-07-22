package com.krispanko.moviematch.repository;

import com.krispanko.moviematch.model.User;
import com.krispanko.moviematch.model.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing Watchlist entities.
 */
public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {
    /**
     * Finds a watchlist by user and movie ID.
     *
     * @param user the user who owns the watchlist
     * @param movieId the ID of the movie
     * @return an optional containing the found watchlist or empty if not found
     */
    Optional<Watchlist> findByUserAndMovieIdsContains(User user, Long movieId);

    /**
     * Finds all watchlists by user.
     *
     * @param user the user who owns the watchlists
     * @return a list of watchlists
     */
    List<Watchlist> findByUser(User user);
}
