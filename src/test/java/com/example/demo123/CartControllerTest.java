package com.example.demo123;

import com.example.demo123.controller.CartController;
import com.example.demo123.model.User;
import com.example.demo123.service.UserService;
import com.example.demo123.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CartControllerTest {

    private UserService userService;
    private JwtUtil jwtUtil;
    private CartController cartController;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        jwtUtil = mock(JwtUtil.class);
        cartController = new CartController(userService, jwtUtil);
    }

    @Test
    void testUpdateCart_Success() {
        // Arrange
        String token = "Bearer fake-jwt-token";
        String email = "test@example.com";
        String cartItems = "{\"items\":[{\"id\":1,\"quantity\":2}]}";
        User user = new User();
        user.setId(1L);
        user.setEmail(email);

        when(jwtUtil.extractEmail("fake-jwt-token")).thenReturn(email);
        when(userService.getUserByEmail(email)).thenReturn(user);
        when(userService.updateUser(user.getId(), user)).thenReturn(user); // Если метод возвращает User

        // Act
        ResponseEntity<?> response = cartController.updateCart(token, cartItems);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Cart updated successfully", response.getBody());
        assertEquals(cartItems, user.getCartItems());
        verify(jwtUtil, times(1)).extractEmail("fake-jwt-token");
        verify(userService, times(1)).getUserByEmail(email);
        verify(userService, times(1)).updateUser(user.getId(), user);
    }


    @Test
    void testUpdateCart_UserNotFound() {
        // Arrange
        String token = "Bearer fake-jwt-token";
        String email = "test@example.com";
        String cartItems = "{\"items\":[{\"id\":1,\"quantity\":2}]}";

        when(jwtUtil.extractEmail("fake-jwt-token")).thenReturn(email);
        when(userService.getUserByEmail(email)).thenReturn(null);

        // Act
        ResponseEntity<?> response = cartController.updateCart(token, cartItems);

        // Assert
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("User not found", response.getBody());
        verify(jwtUtil, times(1)).extractEmail("fake-jwt-token");
        verify(userService, times(1)).getUserByEmail(email);
        verify(userService, never()).updateUser(anyLong(), any());
    }

    @Test
    void testGetCart_Success() {
        // Arrange
        String token = "Bearer fake-jwt-token";
        String email = "test@example.com";
        String cartItems = "{\"items\":[{\"id\":1,\"quantity\":2}]}";
        User user = new User();
        user.setId(1L);
        user.setEmail(email);
        user.setCartItems(cartItems);

        when(jwtUtil.extractEmail("fake-jwt-token")).thenReturn(email);
        when(userService.getUserByEmail(email)).thenReturn(user);

        // Act
        ResponseEntity<?> response = cartController.getCart(token);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(cartItems, response.getBody());
        verify(jwtUtil, times(1)).extractEmail("fake-jwt-token");
        verify(userService, times(1)).getUserByEmail(email);
    }

    @Test
    void testGetCart_UserNotFound() {
        // Arrange
        String token = "Bearer fake-jwt-token";
        String email = "test@example.com";

        when(jwtUtil.extractEmail("fake-jwt-token")).thenReturn(email);
        when(userService.getUserByEmail(email)).thenReturn(null);

        // Act
        ResponseEntity<?> response = cartController.getCart(token);

        // Assert
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("User not found", response.getBody());
        verify(jwtUtil, times(1)).extractEmail("fake-jwt-token");
        verify(userService, times(1)).getUserByEmail(email);
    }
}
