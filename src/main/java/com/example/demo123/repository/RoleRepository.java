package com.example.demo123.repository;

import com.example.demo123.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);  // Метод для поиска роли по имени
}
