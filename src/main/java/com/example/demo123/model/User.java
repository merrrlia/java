package com.example.demo123.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private boolean isAdmin = false; // Флаг админа

    @Column(columnDefinition = "TEXT")
    private String cartItems; // JSON строка для хранения товаров корзины
}
