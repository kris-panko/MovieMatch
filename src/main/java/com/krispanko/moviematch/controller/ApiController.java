package com.krispanko.moviematch.controller;

import com.krispanko.moviematch.model.WatchedMovie;
import com.krispanko.moviematch.model.User;
import com.krispanko.moviematch.repository.UserRepository;
import com.krispanko.moviematch.service.WatchedMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for handling API requests related to movies.
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    private final UserRepository userRepository;
    private final WatchedMovieService watchedMovieService;

    /**
     * Constructs the ApiController with the specified userRepository and watchedMovieService.
     *
     * @param userRepository the user repository
     * @param watchedMovieService the watched movie service
     */
    @Autowired
    public ApiController(UserRepository userRepository, WatchedMovieService watchedMovieService) {
        this.userRepository = userRepository;
        this.watchedMovieService = watchedMovieService;
    }

    /**
     * Handles the rating of a movie.
     *
     * @param movieId the ID of the movie
     * @param rating the rating given by the user
     * @param userDetails the details of the authenticated user
     * @return a response entity containing the rated movie
     */
    @PostMapping("/rate/{movieId}")
    public ResponseEntity<WatchedMovie> rateMovie(@PathVariable Long movieId, @RequestBody int rating, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        WatchedMovie watchedMovie = watchedMovieService.findByUserAndMovieId(user, movieId)
                .orElseGet(() -> new WatchedMovie(movieId, user));
        watchedMovie.setRating(rating);
        watchedMovieService.saveWatchedMovie(watchedMovie);
        return ResponseEntity.ok(watchedMovie);
    }
}
