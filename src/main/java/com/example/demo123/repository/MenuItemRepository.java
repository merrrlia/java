// src/main/java/com/example/demo123/repository/MenuItemRepository.java
package com.example.demo123.repository;

import com.example.demo123.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
}
