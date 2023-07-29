package com.sapientdemo.products.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sapientdemo.products.models.entities.Category;
import com.sapientdemo.products.repositories.CategoryRepository;
import com.sapientdemo.products.utils.GenericResponse;

public class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAllCategories() {
        // Prepare test data
        List<Category> categories = createCategoryList();
        when(categoryRepository.findAll()).thenReturn(categories);

        // Call the service method
        List<Category> result = categoryService.findAllCategories();

        // Verify the result
        assertEquals(categories.size(), result.size());
        assertEquals(categories, result);
    }

    @Test
    public void testFindCategoryById() {
        // Prepare test data
        Long categoryId = 1L;
        Category category = createCategoryList().get(0);
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        // Call the service method
        Category result = categoryService.findCategoryById(categoryId);

        // Verify the result
        assertEquals(category, result);
    }

    @Test
    public void testFindCategoryById_NotFound() {
        // Prepare test data
        Long categoryId = 100L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // Call the service method
        Category result = categoryService.findCategoryById(categoryId);

        // Verify the result
        assertEquals(null, result);
    }

    @Test
    public void testSaveCategory() {
        // Prepare test data
        Category category = createCategoryList().get(0);
        when(categoryRepository.save(category)).thenReturn(category);

        // Call the service method
        categoryService.saveCategory(category);

        // Verify that the repository save method was called once
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    public void testDeleteCategory() {
        // Prepare test data
        Long categoryId = 1L;
        Category category = createCategoryList().get(0);
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        // Call the service method
        categoryService.deleteCategory(categoryId);

        // Verify that the repository delete method was called once with the correct
        // category
        verify(categoryRepository, times(1)).delete(category);
    }

    @Test
    public void testDeleteCategory_NotFound() {
        // Prepare test data
        Long categoryId = 100L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // Call the service method
        GenericResponse result = categoryService.deleteCategory(categoryId);

        // Verify that the repository delete method was not called
        verify(categoryRepository, never()).delete(any(Category.class));

        // Verify the result
        assertFalse(result.isSuccess());
        assertEquals("Category not found", result.getMessage());
    }

    // Private helper function to create an ArrayList of Category objects
    private List<Category> createCategoryList() {
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(Category.builder().id(1L).name("Category 1").description("Description 1").build());
        categoryList.add(Category.builder().id(2L).name("Category 2").description("Description 2").build());
        return categoryList;
    }
}
