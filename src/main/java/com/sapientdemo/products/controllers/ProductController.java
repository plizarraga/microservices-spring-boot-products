package com.sapientdemo.products.controllers;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.sapientdemo.products.models.dtos.InputProduct;
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
        Long categoryId = Long.parseLong(inputProduct.getCategoryId());
        Category category = categoryService.findCategoryById(categoryId);
        if (category == null) {
            return null;
        }
        product.setCategory(category);
        productService.saveProduct(product);

        return product;
    }

    // Update product by id
    @MutationMapping
    public Product updateProduct(@Argument(name = "productId") String id,
            @Argument(name = "inputProduct") InputProduct inputProduct) {
        Long productId = Long.parseLong(id);

        Product existingProduct = productService.findProductById(productId);

        if (existingProduct == null) {
            return null;
        }
        // check if sku is provided
        if (inputProduct.getSku() != null) {
            existingProduct.setSku(inputProduct.getSku());
        }
        // check if name is provided
        if (inputProduct.getName() != null) {
            existingProduct.setName(inputProduct.getName());
        }
        // check if description is provided
        if (inputProduct.getDescription() != null) {
            existingProduct.setDescription(inputProduct.getDescription());
        }
        // check if price is provided
        if (inputProduct.getPrice() != null) {
            existingProduct.setPrice(inputProduct.getPrice());
        }
        // check if status is provided
        if (inputProduct.getStatus() != null) {
            existingProduct.setStatus(inputProduct.getStatus());
        }

        // check if category if provided
        if (inputProduct.getCategoryId() != null) {
            Long categoryId = Long.parseLong(inputProduct.getCategoryId());
            Category category = categoryService.findCategoryById(categoryId);
            if (category == null) {
                return null;
            }
            existingProduct.setCategory(category);
        }

        productService.saveProduct(existingProduct);
        return existingProduct;
    }

    // Delete product by id
    @MutationMapping
    public String deleteProduct(@Argument(name = "productId") String id) {
        Long productId = Long.parseLong(id);

        return productService.deleteProduct(productId).getMessage();
    }
}