package com.krispanko.moviematch.controller;

import com.krispanko.moviematch.model.User;
import com.krispanko.moviematch.model.Watchlist;
import com.krispanko.moviematch.repository.WatchlistRepository;
import com.krispanko.moviematch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/watchlist")
public class WatchlistController {

    @Autowired
    private WatchlistRepository watchlistRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<Watchlist> addToWatchlist(@RequestBody Long movieId, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        Watchlist watchlist = watchlistRepository.findByUserAndMovieIdsContains(user, movieId)
                .orElseGet(() -> {
                    Watchlist newWatchlist = new Watchlist();
                    newWatchlist.setUser(user);
                    return newWatchlist;
                });

        if (!watchlist.getMovieIds().contains(movieId)) {
            watchlist.getMovieIds().add(movieId);
            watchlistRepository.save(watchlist);
        }

        return ResponseEntity.ok(watchlist);
    }

    @GetMapping
    public ResponseEntity<List<Watchlist>> getWatchlist(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        List<Watchlist> watchlist = watchlistRepository.findByUser(user);
        return ResponseEntity.ok(watchlist);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeFromWatchlist(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        Watchlist watchlist = watchlistRepository.findByUser(user).stream()
                .filter(wl -> wl.getMovieIds().contains(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Movie not found in watchlist"));

        watchlist.getMovieIds().remove(id);
        watchlistRepository.save(watchlist);

        return ResponseEntity.noContent().build();
    }
}
