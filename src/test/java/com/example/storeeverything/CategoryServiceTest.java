package com.example.storeeverything;

import com.example.storeeverything.dto.CategoryDto;
import com.example.storeeverything.model.Category;
import com.example.storeeverything.repository.CategoryRepository;
import com.example.storeeverything.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveCategory() {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("TestCategory");
        Category category = new Category();
        category.setName("TestCategory");

        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        categoryService.saveCategory(categoryDto);

        verify(categoryRepository, times(1)).save(any(Category.class));
        assertEquals("TestCategory", category.getName());
    }

    @Test
    public void testGetCategories() {
        Category category = new Category();
        category.setName("TestCategory");
        List<Category> categories = new ArrayList<>();
        categories.add(category);

        when(categoryRepository.findAll()).thenReturn(categories);

        List<CategoryDto> result = categoryService.getCategories();

        verify(categoryRepository, times(1)).findAll();
        assertEquals(1, result.size());
        assertEquals("TestCategory", result.get(0).getName());
    }

    @Test
    public void testGetCategoryByName() {
        Category category = new Category();
        category.setName("TestCategory");

        when(categoryRepository.findByName("TestCategory")).thenReturn(category);

        Category result = categoryService.getCategoryByName("TestCategory");

        verify(categoryRepository, times(1)).findByName("TestCategory");
        assertEquals("TestCategory", result.getName());
    }
}
