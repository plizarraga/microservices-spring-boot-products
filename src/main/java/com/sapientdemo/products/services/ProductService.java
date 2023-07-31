package com.sapientdemo.products.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sapientdemo.products.models.entities.Product;
import com.sapientdemo.products.repositories.ProductRepository;
import com.sapientdemo.products.utils.GenericResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public void saveProduct(Product product) {
        log.info("Product saved: {}", product.getName());
        productRepository.save(product);
    }

    public List<Product> findAllProducts() {
        log.info("Find all products");
        return productRepository.findAll();
    }

    public Product findProductById(Long productId) {
        log.info("Find product by id: {}", productId);
        var product = productRepository.findById(productId).orElse(null);
        return product;
    }

    public GenericResponse deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElse(null);

        if (product == null) {
            log.info("Product deleted by id not found: {}", productId);
            return new GenericResponse(false, "Product not found");
        }

        productRepository.delete(product);
        log.info("Product deleted by id: {}", productId);

        return new GenericResponse(true, "Product deleted successfully");
    }
}
