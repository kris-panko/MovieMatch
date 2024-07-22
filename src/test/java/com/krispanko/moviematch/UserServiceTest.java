package com.krispanko.moviematch;

import com.krispanko.moviematch.model.User;
import com.krispanko.moviematch.repository.UserRepository;
import com.krispanko.moviematch.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveUser() {
        // Arrange
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setEmail("test@example.com");

        // Mock the behavior of the repository and password encoder
        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        userService.saveUser(user);

        // Assert
        verify(passwordEncoder, times(1)).encode("password");
        verify(userRepository, times(1)).save(user);
        assertEquals("encodedPassword", user.getPassword());
    }

    @Test
    public void testFindByUsername() {
        // Arrange
        String username = "testuser";
        User user = new User();
        user.setUsername(username);

        // Mock the behavior of the repository
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // Act
        Optional<User> foundUser = userService.findByUsername(username);

        // Assert
        assertTrue(foundUser.isPresent());
        assertEquals(username, foundUser.get().getUsername());
    }

    @ParameterizedTest
    @ValueSource(strings = {"testuser1", "testuser2", "testuser3"})
    public void testExistsByUsername(String username) {
        // Mock the behavior of the repository
        when(userRepository.existsByUsername(username)).thenReturn(true);

        // Act
        boolean exists = userService.existsByUsername(username);

        // Assert
        assertTrue(exists);
        verify(userRepository, times(1)).existsByUsername(username);
    }

    @Test
    public void testExistsByEmail() {
        // Arrange
        String email = "test@example.com";

        // Mock the behavior of the repository
        when(userRepository.existsByEmail(email)).thenReturn(true);

        // Act
        boolean exists = userService.existsByEmail(email);

        // Assert
        assertTrue(exists);
        verify(userRepository, times(1)).existsByEmail(email);
    }
}
