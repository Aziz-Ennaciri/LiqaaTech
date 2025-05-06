package com.LiqaaTech.Controllers;

import com.LiqaaTech.DTOs.CategoryDTO;
import com.LiqaaTech.Exceptions.NotFoundException;
import com.LiqaaTech.Services.Interf.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    // REST API Endpoints
    @PostMapping("/api")
    @ResponseBody
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.createCategory(categoryDTO));
    }

    @PutMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) throws NotFoundException {
        return ResponseEntity.ok(categoryService.updateCategory(id, categoryDTO));
    }

    @DeleteMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteCategoryApi(@PathVariable Long id) throws NotFoundException {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/{id}")
    @ResponseBody
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @GetMapping("/api")
    @ResponseBody
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    // View Endpoints
    @GetMapping
    public String listCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "categories/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("category", new CategoryDTO());
        return "categories/form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) throws NotFoundException {
        model.addAttribute("category", categoryService.getCategoryById(id));
        return "categories/form";
    }

    @PostMapping("/save")
    public String saveCategory(@ModelAttribute CategoryDTO categoryDTO) throws NotFoundException {
        if (categoryDTO.getId() != null) {
            categoryService.updateCategory(categoryDTO.getId(), categoryDTO);
        } else {
            categoryService.createCategory(categoryDTO);
        }
        return "redirect:/categories";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) throws NotFoundException {
        categoryService.deleteCategory(id);
        return "redirect:/categories";
    }
} 