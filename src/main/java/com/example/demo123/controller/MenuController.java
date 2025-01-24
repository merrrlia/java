// src/main/java/com/example/demo123/controller/MenuController.java
package com.example.demo123.controller;

import com.example.demo123.model.MenuItem;
import com.example.demo123.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    @Autowired
    private MenuItemRepository menuItemRepository;

    // Получить все элементы меню
    @GetMapping
    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }

    // Создать новый элемент меню
    @PostMapping
    public MenuItem createMenuItem(@RequestBody MenuItem menuItem) {
        return menuItemRepository.save(menuItem);
    }

    // Получить элемент меню по ID
    @GetMapping("/{id}")
    public ResponseEntity<MenuItem> getMenuItemById(@PathVariable Long id) {
        return menuItemRepository.findById(id)
                .map(menuItem -> ResponseEntity.ok().body(menuItem))
                .orElse(ResponseEntity.notFound().build());
    }

    // Обновить элемент меню
    @PutMapping("/{id}")
    public ResponseEntity<MenuItem> updateMenuItem(@PathVariable Long id, @RequestBody MenuItem menuDetails) {
        return menuItemRepository.findById(id)
                .map(menuItem -> {
                    menuItem.setName(menuDetails.getName());
                    menuItem.setDescription(menuDetails.getDescription());
                    menuItem.setImageUrl(menuDetails.getImageUrl());
                    MenuItem updatedMenuItem = menuItemRepository.save(menuItem);
                    return ResponseEntity.ok().body(updatedMenuItem);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Удалить элемент меню
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
        if (menuItemRepository.existsById(id)) {
            menuItemRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
