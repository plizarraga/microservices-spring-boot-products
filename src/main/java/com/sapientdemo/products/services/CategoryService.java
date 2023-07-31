package com.sapientdemo.products.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sapientdemo.products.models.entities.Category;
import com.sapientdemo.products.repositories.CategoryRepository;
import com.sapientdemo.products.utils.GenericResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public void saveCategory(Category category) {
        categoryRepository.save(category);
        log.info("Category saved: {}", category.getName());
    }

    public List<Category> findAllCategories() {
        log.info("Find all categories");
        return categoryRepository.findAll();
    }

    public Category findCategoryById(Long categoryId) {
        log.info("Find category by id: {}", categoryId);
        var category = categoryRepository.findById(categoryId).orElse(null);
        return category;
    }

    public GenericResponse deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElse(null);

        if (category == null) {
            log.info("Category deleted by idnot found: {}", categoryId);
            return new GenericResponse(false, "Category not found");
        }

        categoryRepository.delete(category);
        log.info("Category deleted by id: {}", categoryId);

        return new GenericResponse(true, "Category deleted successfully");
    }
}
