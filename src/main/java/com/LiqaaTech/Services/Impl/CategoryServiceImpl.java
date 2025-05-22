package com.LiqaaTech.Services.Impl;

import com.LiqaaTech.Entities.Category;
import com.LiqaaTech.Exceptions.NotFoundException;
import com.LiqaaTech.Repositories.CategoryRepository;
import com.LiqaaTech.Services.Interf.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Category> getAllCategories() {
        return categoryRepository.findAllWithEvents();
    }

    @Override
    @Transactional(readOnly = true)
    public Category getCategoryById(Long id) {
        return categoryRepository.findByIdWithEvents(id)
                .orElseThrow(() -> new NotFoundException("Category not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Category not found with name: " + name));
    }

    @Override
    @Transactional
    public Category createCategory(Category category) {
        if (categoryRepository.existsByName(category.getName())) {
            throw new RuntimeException("Category with this name already exists");
        }
        category.setDeleted(false);
        return categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void ensureDefaultCategories() {
        List<Category> categories = categoryRepository.findAll();
        
        if (categories.isEmpty()) {
            // Create some default categories
            Category general = new Category();
            general.setName("General");
            general.setDescription("General events and activities");
            categoryRepository.save(general);
            
            Category sports = new Category();
            sports.setName("Sports");
            sports.setDescription("Sports and athletic events");
            categoryRepository.save(sports);
            
            Category music = new Category();
            music.setName("Music");
            music.setDescription("Concerts and musical performances");
            categoryRepository.save(music);
        }
    }

    @Override
    @Transactional
    public Category updateCategory(Long id, Category category) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found with id: " + id));
        
        if (!existingCategory.getName().equals(category.getName()) && 
            categoryRepository.existsByName(category.getName())) {
            throw new RuntimeException("Category with this name already exists");
        }
        
        existingCategory.setName(category.getName());
        existingCategory.setDescription(category.getDescription());
        
        return categoryRepository.save(existingCategory);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new NotFoundException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }
} 