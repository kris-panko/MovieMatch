package com.krispanko.moviematch.controller;

import com.krispanko.moviematch.model.User;
import com.krispanko.moviematch.model.WatchedMovie;
import com.krispanko.moviematch.repository.UserRepository;
import com.krispanko.moviematch.repository.WatchedMovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/watched")
public class WatchedMovieController {

    @Autowired
    private WatchedMovieRepository watchedMovieRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<WatchedMovie> addToWatched(@RequestBody WatchedMovie watchedMovie, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        watchedMovie.setUser(user);
        watchedMovieRepository.save(watchedMovie);
        return ResponseEntity.ok(watchedMovie);
    }

    @GetMapping
    public ResponseEntity<List<WatchedMovie>> getWatchedList(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        return ResponseEntity.ok(watchedMovieRepository.findByUser(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeFromWatched(@PathVariable Long id) {
        watchedMovieRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
