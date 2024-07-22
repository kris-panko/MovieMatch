package com.krispanko.moviematch.repository;

import com.krispanko.moviematch.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing Review entities.
 */
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
