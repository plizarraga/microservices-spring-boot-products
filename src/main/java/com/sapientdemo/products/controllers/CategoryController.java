package com.sapientdemo.products.controllers;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.sapientdemo.products.models.dtos.InputCategory;
import com.sapientdemo.products.models.entities.Category;
import com.sapientdemo.products.services.CategoryService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    // Find all categories
    @QueryMapping(name = "findAllCategories")
    public List<Category> findAll() {
        return categoryService.findAllCategories();
    }

    // Find category by id
    @QueryMapping(name = "findCategoryById")
    public Category findById(@Argument(name = "categoryId") String id) {
        Long categoryId = Long.parseLong(id);

        return categoryService.findCategoryById(categoryId);
    }

    // Create category
    @MutationMapping
    public Category createCategory(@Argument InputCategory inputCategory) {
        Category category = new Category();

        category.setName(inputCategory.getName());
        category.setDescription(inputCategory.getDescription());

        categoryService.saveCategory(category);

        return category;
    }

    // Delete category by id
    @MutationMapping
    public String deleteCategory(@Argument(name = "categoryId") String id) {
        Long categoryId = Long.parseLong(id);

        return categoryService.deleteCategory(categoryId).getMessage();
    }

}