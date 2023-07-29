package com.sapientdemo.products.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.sapientdemo.products.models.entities.Product;
import com.sapientdemo.products.repositories.ProductRepository;
import com.sapientdemo.products.utils.GenericResponse;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveProduct() {
        // Arrange
        Product product = createProduct();
        when(productRepository.save(product)).thenReturn(product);

        // Act
        productService.saveProduct(product);

        // Assert
        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void testFindAllProducts() {
        // Arrange
        List<Product> productList = new ArrayList<>();
        productList = createProductList();
        when(productRepository.findAll()).thenReturn(productList);

        // Act
        List<Product> result = productService.findAllProducts();

        // Assert
        assertEquals(productList.size(), result.size());
        assertEquals(productList, result);
    }

    @Test
    public void testFindProductById() {
        // Arrange
        Product product = createProduct();
        Long productId = product.getId();
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Act
        Product result = productService.findProductById(productId);

        // Assert
        assertEquals(product, result);
    }

    @Test
    public void testFindProductById_ProductNotFound() {
        // Arrange
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act
        Product result = productService.findProductById(productId);

        // Assert
        assertEquals(null, result);
    }

    @Test
    public void testDeleteProduct() {
        // Arrange
        Long productId = 1L;
        Product product = createProduct();
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Act
        productService.deleteProduct(productId);

        // Assert
        verify(productRepository, times(1)).delete(product);
    }

    @Test
    public void testDeleteProduct_ProductNotFound() {
        // Arrange
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act
        GenericResponse response = productService.deleteProduct(productId);

        // Assert
        assertEquals(false, response.isSuccess());
        assertEquals("Product not found", response.getMessage());
    }

    // Private helper function to create an ArrayList of Products
    private List<Product> createProductList() {
        List<Product> productList = new ArrayList<>();
        productList
                .add(Product.builder().id(1L).sku("000-1").name("Product 1").description("Product 1 desc").status(true)
                        .price(10.0).build());
        productList
                .add(Product.builder().id(2L).sku("000-2").name("Product 2").description("Product 2 desc").status(true)
                        .price(20.0).build());
        return productList;
    }

    // Private helper function to create a single Product
    private Product createProduct() {
        return Product.builder().id(1L).sku("000-1").name("Product 1").description("Product 1 desc").status(true)
                .price(10.0).build();
    }
}