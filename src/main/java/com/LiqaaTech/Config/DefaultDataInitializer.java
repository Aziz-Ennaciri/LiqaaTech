package com.LiqaaTech.Config;

import com.LiqaaTech.Services.Interf.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DefaultDataInitializer implements CommandLineRunner {
    
    private final CategoryService categoryService;

    @Autowired
    public DefaultDataInitializer(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws Exception {
        // Ensure default categories exist
        categoryService.ensureDefaultCategories();
    }
}
