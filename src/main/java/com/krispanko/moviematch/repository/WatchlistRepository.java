package com.krispanko.moviematch.repository;

import com.krispanko.moviematch.model.User;
import com.krispanko.moviematch.model.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {
    Optional<Watchlist> findByUserAndMovieIdsContains(User user, Long movieId);
    List<Watchlist> findByUser(User user);
}
