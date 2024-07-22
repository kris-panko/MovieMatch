package com.krispanko.moviematch.controller;

import com.krispanko.moviematch.model.User;
import com.krispanko.moviematch.model.Watchlist;
import com.krispanko.moviematch.repository.UserRepository;
import com.krispanko.moviematch.repository.WatchlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/watchlist")
public class WatchlistController {

    @Autowired
    private WatchlistRepository watchlistRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<Map<String, String>> addToWatchlist(@RequestBody Map<String, Object> movieData, Principal principal) {
        try {
            Optional<User> optionalUser = userRepository.findByUsername(principal.getName());

            if (optionalUser.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "User not found"));
            }

            User user = optionalUser.get();

            Watchlist watchlist;
            List<Watchlist> userWatchlists = watchlistRepository.findByUser(user);
            if (!userWatchlists.isEmpty()) {
                watchlist = userWatchlists.get(0);
            } else {
                watchlist = new Watchlist();
                watchlist.setUser(user);
                watchlist.setMovieIds(new ArrayList<>());
            }

            if (watchlist.getMovieIds() == null) {
                watchlist.setMovieIds(new ArrayList<>());
            }

            Long movieId = Long.valueOf((String) movieData.get("movieId"));
            if (!watchlist.getMovieIds().contains(movieId)) {
                watchlist.getMovieIds().add(movieId);
                watchlistRepository.save(watchlist);
            }

            return ResponseEntity.ok(Map.of("message", "Movie added to watchlist"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error adding movie to watchlist"));
        }
    }
}
