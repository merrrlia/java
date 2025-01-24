// src/main/java/com/example/demo123/config/DataInitializer.java
package com.example.demo123.config;

import com.example.demo123.model.MenuItem;
import com.example.demo123.model.Role;
import com.example.demo123.model.User;
import com.example.demo123.repository.RoleRepository;
import com.example.demo123.repository.UserRepository;
import com.example.demo123.repository.MenuItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        // Инициализация ролей
        Role adminRole = new Role("ADMIN");
        Role userRole = new Role("USER");
        roleRepository.save(adminRole);
        roleRepository.save(userRole);

        // Инициализация пользователей
        User admin = new User("Admin User");
        admin.setRoles(new HashSet<>());
        admin.getRoles().add(adminRole);
        userRepository.save(admin);

        User user = new User("Regular User");
        user.setRoles(new HashSet<>());
        user.getRoles().add(userRole);
        userRepository.save(user);

        // Инициализация элементов меню
        if (menuItemRepository.count() == 0) {
            MenuItem espresso = new MenuItem("Espresso", "Strong and bold shot of coffee.", "https://st.fl.ru/users/bh/bhcre8ive/upload/f_38362b615444bf4d.png");
            MenuItem latte = new MenuItem("Latte", "Creamy blend of coffee and steamed milk.", "https://st.fl.ru/users/bh/bhcre8ive/upload/f_38362b615444bf4d.png");
            MenuItem cappuccino = new MenuItem("Cappuccino", "Perfect mix of espresso, steamed milk, and foam.", "https://st.fl.ru/users/bh/bhcre8ive/upload/f_38362b615444bf4d.png");
            // Добавьте остальные элементы меню

            menuItemRepository.save(espresso);
            menuItemRepository.save(latte);
            menuItemRepository.save(cappuccino);
            // Сохраните остальные элементы меню
        }
    }
}
