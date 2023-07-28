package com.sapientdemo.products.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sapientdemo.products.models.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
