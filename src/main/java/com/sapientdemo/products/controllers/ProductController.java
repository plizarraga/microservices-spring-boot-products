package com.sapientdemo.products.controllers;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.sapientdemo.products.models.dto.InputProduct;
import com.sapientdemo.products.models.entities.Category;
import com.sapientdemo.products.models.entities.Product;
import com.sapientdemo.products.services.CategoryService;
import com.sapientdemo.products.services.ProductService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    // Find all products
    @QueryMapping(name = "findAllProducts")
    public List<Product> findAll() {
        return productService.findAllProducts();
    }

    // Find product by id
    @QueryMapping(name = "findProductById")
    public Product findById(@Argument(name = "productId") String id) {
        Long productId = Long.parseLong(id);

        return productService.findProductById(productId);
    }

    // Create product
    @MutationMapping
    public Product createProduct(@Argument InputProduct inputProduct) {
        Product product = new Product();

        product.setSku(inputProduct.getSku());
        product.setName(inputProduct.getName());
        product.setDescription(inputProduct.getDescription());
        product.setPrice(inputProduct.getPrice());
        product.setStatus(inputProduct.getStatus());
        product.setPrice(inputProduct.getPrice());

        // Set category
        Long categoryId = Long.parseLong(inputProduct.getCategorylId());
        Category category = categoryService.findCategoryById(categoryId);
        if (category == null) {
            return null;
        }
        product.setCategory(category);
        productService.saveProduct(product);

        return product;
    }

    // Delete product by id
    @MutationMapping
    public String deleteProduct(@Argument(name = "productId") String id) {
        Long productId = Long.parseLong(id);

        return productService.deleteProduct(productId).getMessage();
    }
}