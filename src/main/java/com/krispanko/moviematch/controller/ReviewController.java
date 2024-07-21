package com.krispanko.moviematch.controller;

import com.krispanko.moviematch.model.Review;
import com.krispanko.moviematch.model.User;
import com.krispanko.moviematch.repository.UserRepository;
import com.krispanko.moviematch.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final UserRepository userRepository;

    @Autowired
    public ReviewController(ReviewService reviewService, UserRepository userRepository) {
        this.reviewService = reviewService;
        this.userRepository = userRepository;
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<List<Review>> getReviewsByMovie(@PathVariable Long movieId) {
        List<Review> reviews = reviewService.findByMovieId(movieId);
        return ResponseEntity.ok(reviews);
    }

    @PostMapping("/{movieId}")
    public ResponseEntity<Review> addReview(@PathVariable Long movieId, @RequestBody Review review, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        review.setMovieId(movieId);
        review.setUser(user);
        Review savedReview = reviewService.saveReview(review);
        return ResponseEntity.ok(savedReview);
    }
}
