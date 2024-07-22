package com.krispanko.moviematch.service;

import com.krispanko.moviematch.model.Review;
import com.krispanko.moviematch.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing reviews.
 */
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    /**
     * Constructs the ReviewService with the specified reviewRepository.
     *
     * @param reviewRepository the review repository
     */
    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    /**
     * Retrieves all reviews.
     *
     * @return a list of reviews
     */
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    /**
     * Saves a review.
     *
     * @param review the review to be saved
     * @return the saved review
     */
    public Review saveReview(Review review) {
        return reviewRepository.save(review);
    }
}
