package com.sapientdemo.products.models.dto;

import lombok.Data;

@Data
public class InputProduct {
    private String sku;
    private String name;
    private String description;
    private Double price;
    private Boolean status;
    private String categorylId;
}
