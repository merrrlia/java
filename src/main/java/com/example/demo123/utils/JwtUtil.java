package com.example.demo123.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    // Ваш Base64-encoded ключ. Замените на ваш действительный ключ.
    private static final String SECRET_KEY = "c2VjcmV0a2V5c2VjcmV0a2V5c2VjcmV0a2V5c2VjcmV0a2V5c2VjcmV0a2V5"; // Пример: "c2VjcmV0a2V5c2VjcmV0a2V5c2VjcmV0a2V5c2VjcmV0a2V5c2VjcmV0a2V5"
    private static final long EXPIRATION_TIME = 86400000; // 1 день в миллисекундах

    private Key getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Метод для генерации токена
    public String generateToken(String email) {
        String token = Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
        logger.info("Сгенерирован токен для email {}: {}", email, token);
        return token;
    }

    // Метод для валидации токена
    public boolean validateToken(String token, String email) {
        try {
            String extractedEmail = extractEmail(token);
            boolean isValid = (email.equals(extractedEmail) && !isTokenExpired(token));
            logger.info("Валидация токена для email {}: {}", email, isValid);
            return isValid;
        } catch (JwtException e) {
            logger.error("Токен не валиден: {}", e.getMessage());
            return false;
        }
    }
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Извлечение email из токена
    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    // Проверка, не истек ли токен
    public boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    // Получение всех claims из токена
    private Claims getClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            logger.error("Ошибка при разборе JWT: {}", e.getMessage());
            throw e;
        }
    }
}
