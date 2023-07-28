package com.sapientdemo.products.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sapientdemo.products.models.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
