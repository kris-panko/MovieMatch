package com.krispanko.moviematch.repository;

import com.krispanko.moviematch.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Repository interface for managing User entities.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Finds a user by their username.
     *
     * @param username the username to search for
     * @return an optional containing the found user or empty if not found
     */
    Optional<User> findByUsername(String username);

    /**
     * Checks if a user exists by their username.
     *
     * @param username the username to check for
     * @return true if the user exists, false otherwise
     */
    boolean existsByUsername(String username);

    /**
     * Checks if a user exists by their email.
     *
     * @param email the email to check for
     * @return true if the user exists, false otherwise
     */
    boolean existsByEmail(String email);
}
