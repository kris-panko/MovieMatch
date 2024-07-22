package com.krispanko.moviematch;

import com.krispanko.moviematch.model.User;
import com.krispanko.moviematch.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void cleanUp() {
        userRepository.deleteAll();
    }


    @Test
    public void testExistsByUsername() {
        // Arrange: Create and save a user to the repository
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setEmail("testuser@example.com");
        userRepository.save(user);

        // Act: Check if the username exists in the repository
        boolean exists = userRepository.existsByUsername("testuser");

        // Assert: Verify the username exists
        assertTrue(exists);
    }

    @Test
    public void testExistsByEmail() {
        // Arrange: Create and save a user to the repository
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setEmail("testuser@example.com");
        userRepository.save(user);

        // Act: Check if the email exists in the repository
        boolean exists = userRepository.existsByEmail("testuser@example.com");

        // Assert: Verify the email exists
        assertTrue(exists);
    }
}
