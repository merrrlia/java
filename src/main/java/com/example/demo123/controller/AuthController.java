package com.example.demo123.controller;

import com.example.demo123.model.User;
import com.example.demo123.service.UserService;
import com.example.demo123.utils.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userService.getUserByEmail(user.getEmail()) != null) {
            System.out.println("Email already in use: " + user);
            return ResponseEntity.status(400).body("Email already in use");
        }

        userService.registerUser(user.getEmail(), user.getPassword());
        return ResponseEntity.ok("User registered successfully");

    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        System.out.println("loginUser: " + user);
        User foundUser = userService.getUserByEmail(user.getEmail());
        System.out.println("foundUser: " + foundUser);
        //System.out.println("passwordEncoder: " + passwordEncoder.matches(user.getPassword(), foundUser.getPassword()));

        if (foundUser != null && passwordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
            String token = jwtUtil.generateToken(foundUser.getEmail());

            // Формируем ответ с информацией о пользователе и токеном
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("isAdmin", foundUser.isAdmin()); // Проверка роли пользователя

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(Map.of("message", "Неверный пароль. Попробуйте снова."));
        }
    }


}
