// src/main/java/com/example/coffeeshop/controller/MenuController.java
package com.example.demo123.controller;

import com.example.demo123.model.MenuItem;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MenuController {

    @GetMapping("/api/menu")
    public Map<String, List<MenuItem>> getMenu() {
        Map<String, List<MenuItem>> menuData = new HashMap<>();

        menuData.put("Coffee", Arrays.asList(
                new MenuItem("Americano", "Strong and bold shot of coffee.", "https://st.fl.ru/users/bh/bhcre8ive/upload/f_38362b615444bf4d.png"),
                new MenuItem("Latte", "Creamy blend of coffee and steamed milk.", "https://st.fl.ru/users/bh/bhcre8ive/upload/f_38362b615444bf4d.png"),
                new MenuItem("Americano", "Strong and bold shot of coffee.", "https://st.fl.ru/users/bh/bhcre8ive/upload/f_38362b615444bf4d.png"),
                new MenuItem("Latte", "Creamy blend of coffee and steamed milk.", "https://st.fl.ru/users/bh/bhcre8ive/upload/f_38362b615444bf4d.png"),
                new MenuItem("Espresso", "Strong and bold shot of coffee.", "https://st.fl.ru/users/bh/bhcre8ive/upload/f_38362b615444bf4d.png"),
                new MenuItem("Latte", "Creamy blend of coffee and steamed milk.", "https://st.fl.ru/users/bh/bhcre8ive/upload/f_38362b615444bf4d.png"),
                new MenuItem("Cappuccino", "Perfect mix of espresso, steamed milk, and foam.", "https://st.fl.ru/users/bh/bhcre8ive/upload/f_38362b615444bf4d.png"),
                new MenuItem("Espresso", "Strong and bold shot of coffee.", "https://st.fl.ru/users/bh/bhcre8ive/upload/f_38362b615444bf4d.png"),
                new MenuItem("Latte", "Creamy blend of coffee and steamed milk.", "https://st.fl.ru/users/bh/bhcre8ive/upload/f_38362b615444bf4d.png"),
                new MenuItem("Espresso", "Strong and bold shot of coffee.", "https://st.fl.ru/users/bh/bhcre8ive/upload/f_38362b615444bf4d.png"),
                new MenuItem("Latte", "Creamy blend of coffee and steamed milk.", "https://st.fl.ru/users/bh/bhcre8ive/upload/f_38362b615444bf4d.png"),
                new MenuItem("Cappuccino", "Perfect mix of espresso, steamed milk, and foam.", "https://st.fl.ru/users/bh/bhcre8ive/upload/f_38362b615444bf4d.png"),
                new MenuItem("Espresso", "Strong and bold shot of coffee.", "https://st.fl.ru/users/bh/bhcre8ive/upload/f_38362b615444bf4d.png"),
                new MenuItem("Latte", "Creamy blend of coffee and steamed milk.", "https://st.fl.ru/users/bh/bhcre8ive/upload/f_38362b615444bf4d.png"),
                new MenuItem("Cappuccino", "Perfect mix of espresso, steamed milk, and foam.", "https://st.fl.ru/users/bh/bhcre8ive/upload/f_38362b615444bf4d.png")
        // Добавьте остальные кофе
        ));

        menuData.put("Tea", Arrays.asList(
                new MenuItem("Green Tea", "Refreshing and healthy green tea.", "https://st.fl.ru/users/bh/bhcre8ive/upload/f_38362b615444bf4d.png"),
                new MenuItem("Chai Latte", "Aromatic blend of tea, spices, and milk.", "https://st.fl.ru/users/bh/bhcre8ive/upload/f_38362b615444bf4d.png"),
                new MenuItem("Black Tea", "Classic black tea.", "https://st.fl.ru/users/bh/bhcre8ive/upload/f_38362b615444bf4d.png")
                // Добавьте остальные чаи
        ));

        menuData.put("Pastries", Arrays.asList(
                new MenuItem("Croissant", "Flaky and buttery French pastry.", "https://st.fl.ru/users/bh/bhcre8ive/upload/f_38362b615444bf4d.png"),
                new MenuItem("Muffin", "Soft and delicious baked muffin.", "https://st.fl.ru/users/bh/bhcre8ive/upload/f_38362b615444bf4d.png"),
                new MenuItem("Scone", "Biscuit-like treat.", "https://st.fl.ru/users/bh/bhcre8ive/upload/f_38362b615444bf4d.png")
                // Добавьте остальные выпечки
        ));

        return menuData;
    }
}
