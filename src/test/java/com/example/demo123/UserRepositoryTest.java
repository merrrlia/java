package com.example.demo123;

import com.example.demo123.model.User;
import com.example.demo123.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test") // Использует конфигурацию из application.properties для тестов
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        // Добавление тестового пользователя перед каждым тестом
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john@example.com");
        user.setPassword("password");
        user.setAdmin(false);
        userRepository.save(user);
    }

    @Test
    void testFindByEmail_UserFound() {
        // Act
        Optional<User> result = userRepository.findByEmail("john@example.com");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getName());
    }

    @Test
    void testFindByEmail_UserNotFound() {
        // Act
        Optional<User> result = userRepository.findByEmail("jane@example.com");

        // Assert
        assertTrue(result.isEmpty());
    }
}
