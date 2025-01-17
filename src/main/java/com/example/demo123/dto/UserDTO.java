package com.example.demo123.dto;

import com.example.demo123.model.Role;  // Убедитесь, что используете правильный Role
import java.util.Set;

public class UserDTO {
    private Long id;
    private String name;
    private Set<Role> roles;  // Используем ваш собственный класс Role

    public UserDTO(Long id, String name, Set<Role> roles) {
        this.id = id;
        this.name = name;
        this.roles = roles;
    }

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
