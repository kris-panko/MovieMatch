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

@RestController
@RequestMapping("/api")
public class ApiController {

    private final UserRepository userRepository;
    private final WatchedMovieService watchedMovieService;

    @Autowired
    public ApiController(UserRepository userRepository, WatchedMovieService watchedMovieService) {
        this.userRepository = userRepository;
        this.watchedMovieService = watchedMovieService;
    }

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
