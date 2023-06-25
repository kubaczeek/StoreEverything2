
package com.example.storeeverything.controller;

import com.example.storeeverything.dto.CategoryDto;
import com.example.storeeverything.service.CategoryService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CategoryController {
    private final CategoryService categoryService;
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping({"/add-category"})
    public String showAddCategoryForm(Model model) {
        model.addAttribute("category", new CategoryDto());
        logger.info("/categories");
        return "add-category";
    }

    @PostMapping({"/categories/add"})
    public String addCategory(@ModelAttribute("category") @Valid CategoryDto category, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("category", category);
            return "/add-category";
        } else {
            this.categoryService.saveCategory(category);
            logger.info("/categories/add");
            return "redirect:/user-notes";
        }
    }
}
