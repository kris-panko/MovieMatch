package com.krispanko.moviematch.service;

import com.krispanko.moviematch.model.User;

import java.util.Optional;

public interface UserService {
    void saveUser(User user);
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
