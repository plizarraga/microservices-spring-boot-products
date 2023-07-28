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
        productRepository.save(product);
        log.info("Product added: {}", product);
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Product findProductById(Long productId) {
        var product = productRepository.findById(productId).orElse(null);
        return product;
    }

    public GenericResponse deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElse(null);

        if (product == null) {
            return new GenericResponse(false, "Product not found");
        }

        productRepository.delete(product);
        log.info("Product deleted: {}", product);

        return new GenericResponse(true, "Product deleted successfully");
    }
}
