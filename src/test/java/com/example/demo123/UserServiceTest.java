package com.example.demo123;



import com.example.demo123.model.User;
import com.example.demo123.repository.UserRepository;
import com.example.demo123.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser() {
        // Arrange
        String email = "test@example.com";
        String rawPassword = "password";
        String encodedPassword = "encodedPassword";
        User user = new User();
        user.setEmail(email);
        user.setPassword(encodedPassword);

        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        User result = userService.registerUser(email, rawPassword);

        // Assert
        assertNotNull(result);
        assertEquals(email, result.getEmail());
        assertEquals(encodedPassword, result.getPassword());
        verify(passwordEncoder, times(1)).encode(rawPassword);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testGetUserByEmail_UserFound() {
        // Arrange
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // Act
        User result = userService.getUserByEmail(email);

        // Assert
        assertNotNull(result);
        assertEquals(email, result.getEmail());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void testGetUserByEmail_UserNotFound() {
        // Arrange
        String email = "test@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act
        User result = userService.getUserByEmail(email);

        // Assert
        assertNull(result);
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void testUpdateUser_UserFound() {
        // Arrange
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setName("Old Name");
        existingUser.setCartItems("{\"items\":[]}");

        User updatedDetails = new User();
        updatedDetails.setName("New Name");
        updatedDetails.setCartItems("{\"items\":[{\"id\":1,\"quantity\":2}]}");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        // Act
        User result = userService.updateUser(userId, updatedDetails);

        // Assert
        assertNotNull(result);
        assertEquals("New Name", result.getName());
        assertEquals("{\"items\":[{\"id\":1,\"quantity\":2}]}", result.getCartItems());
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void testUpdateUser_UserNotFound() {
        // Arrange
        Long userId = 1L;
        User updatedDetails = new User();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.updateUser(userId, updatedDetails);
        });

        assertEquals("User not found with ID: " + userId, exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testDeleteUser() {
        // Arrange
        Long userId = 1L;
        doNothing().when(userRepository).deleteById(userId);

        // Act
        userService.deleteUser(userId);

        // Assert
        verify(userRepository, times(1)).deleteById(userId);
    }
}

