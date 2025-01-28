package com.example.demo123;

import com.example.demo123.controller.AuthController;
import com.example.demo123.model.User;
import com.example.demo123.service.UserService;
import com.example.demo123.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    private UserService userService;
    private JwtUtil jwtUtil;
    private PasswordEncoder passwordEncoder;
    private AuthController authController;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        jwtUtil = mock(JwtUtil.class);
        passwordEncoder = mock(PasswordEncoder.class);
        authController = new AuthController(userService, jwtUtil, passwordEncoder);
    }

    @Test
    void testRegisterUser_Success() {
        // Arrange
        String email = "test@example.com";
        String password = "password";
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        when(userService.getUserByEmail(email)).thenReturn(null); // Email свободен
        when(userService.registerUser(email, password)).thenReturn(user); // Возвращаем созданного пользователя

        // Act
        ResponseEntity<?> response = authController.registerUser(user);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User registered successfully", response.getBody());
        verify(userService, times(1)).getUserByEmail(email);
        verify(userService, times(1)).registerUser(email, password);
    }


    @Test
    void testRegisterUser_EmailAlreadyInUse() {
        // Arrange
        String email = "test@example.com";
        String password = "password";
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        when(userService.getUserByEmail(email)).thenReturn(user);

        // Act
        ResponseEntity<?> response = authController.registerUser(user);

        // Assert
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Email already in use", response.getBody());
        verify(userService, times(1)).getUserByEmail(email);
        verify(userService, never()).registerUser(email, password);
    }

    @Test
    void testLoginUser_Success() {
        // Arrange
        String email = "test@example.com";
        String password = "password";
        String token = "fake-jwt-token";
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setAdmin(false);

        when(userService.getUserByEmail(email)).thenReturn(user);
        when(passwordEncoder.matches(password, password)).thenReturn(true);
        when(jwtUtil.generateToken(email)).thenReturn(token);

        // Act
        ResponseEntity<?> response = authController.loginUser(user);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals(token, responseBody.get("token"));
        assertEquals(false, responseBody.get("isAdmin"));
        verify(userService, times(1)).getUserByEmail(email);
        verify(passwordEncoder, times(1)).matches(password, password);
        verify(jwtUtil, times(1)).generateToken(email);
    }

    @Test
    void testLoginUser_InvalidCredentials() {
        // Arrange
        String email = "test@example.com";
        String password = "wrong-password";
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        when(userService.getUserByEmail(email)).thenReturn(user);
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(false);

        // Act
        ResponseEntity<?> response = authController.loginUser(user);

        // Assert
        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Invalid credentials", response.getBody());
        verify(userService, times(1)).getUserByEmail(email);
        verify(passwordEncoder, times(1)).matches(password, user.getPassword());
        verify(jwtUtil, never()).generateToken(email);
    }
}

