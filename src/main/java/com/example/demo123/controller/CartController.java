package com.example.demo123.controller;

import com.example.demo123.model.User;
import com.example.demo123.service.UserService;
import com.example.demo123.utils.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public CartController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateCart(@RequestHeader("Authorization") String token, @RequestBody String cartItems) {
        String email = jwtUtil.extractEmail(token.substring(7));
        User user = userService.getUserByEmail(email);

        if (user == null) {
            return ResponseEntity.status(404).body("User not found");
        }

        user.setCartItems(cartItems);
        userService.updateUser(user);

        return ResponseEntity.ok("Cart updated successfully");
    }

    @GetMapping("/get")
    public ResponseEntity<?> getCart(@RequestHeader("Authorization") String token) {
        String email = jwtUtil.extractEmail(token.substring(7));
        User user = userService.getUserByEmail(email);

        if (user == null) {
            return ResponseEntity.status(404).body("User not found");
        }

        return ResponseEntity.ok(user.getCartItems());
    }
}
