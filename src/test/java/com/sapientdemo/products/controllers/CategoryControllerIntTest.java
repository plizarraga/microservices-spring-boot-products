package com.sapientdemo.products.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;

import com.sapientdemo.products.models.entities.Category;
import com.sapientdemo.products.services.CategoryService;

@GraphQlTest(CategoryController.class)
public class CategoryControllerIntTest {
    @Autowired
    private GraphQlTester graphQlTester;

    @MockBean
    private CategoryService categoryService;

    @Test
    void testFindAllCategoriesShouldReturnAllCategories() {

        List<Category> categories = createCategoryList();
        when(categoryService.findAllCategories()).thenReturn(categories);

        String document = """
                query {
                    findAllCategories {
                        id
                        name
                        description
                    }
                }
                """;

        graphQlTester.document(document)
                .execute()
                .path("findAllCategories")
                .entityList(Category.class)
                .hasSize(2);
    }

    @Test
    void shouldGetFirstCategory() {

        Category category = new Category();
        category.setId(1L);
        category.setName("Electronics");
        category.setDescription("Electronics is the most popular electronics store in the world.");
        when(categoryService.findCategoryById(anyLong())).thenReturn(category);

        String document = """
                query findCategoryById($id: String) {
                     findCategoryById(categoryId: $id) {
                        id
                        name
                        description
                    }
                  }
                    """;

        this.graphQlTester
                .document(document)
                .variable("id", "1")
                .execute()
                .path("findCategoryById")
                .entity(Category.class)
                .satisfies(resultCategory -> {
                    assertEquals(category.getName(), resultCategory.getName());
                    assertEquals(category.getDescription(), resultCategory.getDescription());
                });
    }

    @Test
    void shouldReturnNotFound() {

        when(categoryService.findCategoryById(anyLong())).thenReturn(null);

        String document = """
                query findCategoryById($id: String) {
                     findCategoryById(categoryId: $id) {
                        id
                        name
                        description
                    }
                  }
                    """;

        this.graphQlTester
                .document(document)
                .variable("id", "100")
                .execute()
                .path("findCategoryById")
                .valueIsNull();
    }

    // Private helper function to create an ArrayList of Category objects
    private List<Category> createCategoryList() {
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(Category.builder().id(1L).name("Category 1").description("Description 1").build());
        categoryList.add(Category.builder().id(2L).name("Category 2").description("Description 2").build());
        return categoryList;
    }
}
