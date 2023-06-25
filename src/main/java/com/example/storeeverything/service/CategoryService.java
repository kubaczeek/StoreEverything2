package com.example.storeeverything.service;

import com.example.storeeverything.dto.CategoryDto;
import com.example.storeeverything.model.Category;
import com.example.storeeverything.repository.CategoryRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void saveCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        this.categoryRepository.save(category);
    }

    public List<CategoryDto> getCategories() {
        List<Category> categories = this.categoryRepository.findAll();
        return this.mapCategoriesToDtos(categories);
    }

    public Category getCategoryByName(String name) {
        return this.categoryRepository.findByName(name);
    }

    private List<CategoryDto> mapCategoriesToDtos(List<Category> categories) {
        return (List)categories.stream().map((category) -> {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId(category.getId());
            categoryDto.setName(category.getName());
            return categoryDto;
        }).collect(Collectors.toList());
    }
}
