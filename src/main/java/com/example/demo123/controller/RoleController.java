package com.example.demo123.controller;

import com.example.demo123.model.Role;
import com.example.demo123.model.User;
import com.example.demo123.repository.RoleRepository;
import com.example.demo123.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    // Создание новой роли
    @PostMapping
    public Role createRole(@RequestBody Role role) {
        return roleRepository.save(role);
    }

    // Получение всех ролей
    @GetMapping
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    // Получение роли по ID
    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
        Optional<Role> role = roleRepository.findById(id);
        return role.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Обновление роли
    @PutMapping("/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable Long id, @RequestBody Role roleDetails) {
        return roleRepository.findById(id)
                .map(role -> {
                    role.setName(roleDetails.getName()); // Обновляем имя роли
                    Role updatedRole = roleRepository.save(role);
                    return ResponseEntity.ok().body(updatedRole);
                })
                .orElse(ResponseEntity.notFound().build());
    }

//    // Удаление роли
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
//        return roleRepository.findById(id)
//                .map(role -> {
//                    roleRepository.delete(role);
//                    return ResponseEntity.noContent().build();
//                })
//                .orElse(ResponseEntity.notFound().build());
//    }

    // Присваивание роли пользователю
    @PostMapping("/{roleId}/users/{userId}")
    public ResponseEntity<User> assignRoleToUser(@PathVariable Long roleId, @PathVariable Long userId) {
        Optional<Role> role = roleRepository.findById(roleId);
        Optional<User> user = userRepository.findById(userId);

        if (role.isPresent() && user.isPresent()) {
            User foundUser = user.get();
            Role foundRole = role.get();
            System.out.println(foundUser);
            System.out.println(foundRole);

            Set<Role> roles = foundUser.getRoles();
            if (roles == null) {
                roles = new HashSet<>();
            }
            roles.add(foundRole);
            foundUser.setRoles(roles);
            userRepository.save(foundUser);
            return ResponseEntity.ok(foundUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
