package com.krispanko.moviematch.service;

import com.krispanko.moviematch.model.User;
import com.krispanko.moviematch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of UserService for managing user-related operations.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs the UserServiceImpl with the specified userRepository and passwordEncoder.
     *
     * @param userRepository the user repository
     * @param passwordEncoder the password encoder
     */
    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Saves a user after encoding their password.
     *
     * @param user the user to be saved
     */
    @Override
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    /**
     * Finds a user by their username.
     *
     * @param username the username to search for
     * @return an optional containing the found user or empty if not found
     */
    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Checks if a user exists by their username.
     *
     * @param username the username to check for
     * @return true if the user exists, false otherwise
     */
    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    /**
     * Checks if a user exists by their email.
     *
     * @param email the email to check for
     * @return true if the user exists, false otherwise
     */
    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
