package com.LiqaaTech.Services.Interf;

import com.LiqaaTech.DTOs.CategoryDTO;
import com.LiqaaTech.Exceptions.NotFoundException;

import java.util.List;

public interface CategoryService {
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) throws NotFoundException;
    void deleteCategory(Long id) throws NotFoundException;
    CategoryDTO getCategoryById(Long id) throws NotFoundException;
    List<CategoryDTO> getAllCategories();
} 