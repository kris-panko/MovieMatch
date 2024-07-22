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

/**
 * REST controller for managing watched movies.
 */
@RestController
@RequestMapping("/api/watched")
public class WatchedMovieController {

    private final WatchedMovieRepository watchedMovieRepository;
    private final UserRepository userRepository;

    /**
     * Constructs the WatchedMovieController with the specified watchedMovieRepository and userRepository.
     *
     * @param watchedMovieRepository the watched movie repository
     * @param userRepository the user repository
     */
    @Autowired
    public WatchedMovieController(WatchedMovieRepository watchedMovieRepository, UserRepository userRepository) {
        this.watchedMovieRepository = watchedMovieRepository;
        this.userRepository = userRepository;
    }

    /**
     * Adds a movie to the watched list.
     *
     * @param watchedMovie the movie to be added
     * @param userDetails the details of the authenticated user
     * @return a response entity containing the added movie
     */
    @PostMapping
    public ResponseEntity<WatchedMovie> addToWatched(@RequestBody WatchedMovie watchedMovie, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        watchedMovie.setUser(user);
        watchedMovieRepository.save(watchedMovie);
        return ResponseEntity.ok(watchedMovie);
    }

    /**
     * Retrieves the watched list of the authenticated user.
     *
     * @param userDetails the details of the authenticated user
     * @return a response entity containing the list of watched movies
     */
    @GetMapping
    public ResponseEntity<List<WatchedMovie>> getWatchedList(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        return ResponseEntity.ok(watchedMovieRepository.findByUser(user));
    }

    /**
     * Removes a movie from the watched list by its ID.
     *
     * @param id the ID of the movie to be removed
     * @return a response entity with no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeFromWatched(@PathVariable Long id) {
        watchedMovieRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
