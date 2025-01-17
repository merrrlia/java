package com.example.demo123.controller;

import com.example.demo123.dto.UserDTO;
import com.example.demo123.exception.ResourceNotFoundException;
import com.example.demo123.model.User;
import com.example.demo123.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users") // базовый путь для всех маршрутов этого контроллера
public class UserController {

    @Autowired
    private UserRepository userRepository; // репозиторий для работы с базой данных

    // Получить всех пользователей
    @GetMapping()
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> new UserDTO(user.getId(), user.getName(), user.getRoles()))
                .collect(Collectors.toList());
    }


    // Получить пользователя по ID
     @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
        return ResponseEntity.ok().body(user);
    }
    // Создать нового пользователя
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

//    // Обновить пользователя
//    @PutMapping("/{id}")
//    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
//        return userRepository.findById(id)
//                .map(user -> {
//                    user.setName(userDetails.getName()); // обновляем имя пользователя
//                    User updatedUser = userRepository.save(user);
//                    return ResponseEntity.ok().body(updatedUser);
//                })
//                .orElse(ResponseEntity.notFound().build());
//    }
    // Обновить пользователя
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setName(userDetails.getName()); // обновляем имя пользователя
                    // Добавьте здесь другие поля для обновления, если нужно
                    User updatedUser = userRepository.save(user);
                    return ResponseEntity.ok().body(updatedUser);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Удалить пользователя
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
