package com.example.demo123.service;

import com.example.demo123.model.User;
import com.example.demo123.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Регистрация пользователя
    public User registerUser(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    // Получение пользователя по email
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    // Обновление данных пользователя (например, корзины или роли)
    public User updateUser(Long userId, User userDetails) {
        return userRepository.findById(userId)
                .map(existingUser -> {
                    existingUser.setName(userDetails.getName());
                    existingUser.setCartItems(userDetails.getCartItems());
                    existingUser.setAdmin(userDetails.isAdmin());
                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
