package com.kpi.springboot_laba_2.entity;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "prototype")
public class Product {
    private int id;
    @Size(max = 100, message = "Name must be less than 100 characters.")
    @Pattern(
            regexp = "^(?:(?!<img|<a hre|<div on|<script|<input|url\\(javas|http).)*$",
            message = "Invalid characters in the name."
    )
    private String name;
    @Pattern(
            regexp = "^(?:(?!<img|<a hre|<div on|<script|<input|url\\(javas|http).)*$",
            message = "Invalid characters in the description."
    )
    private String description;

    private Double price;
    private int categoryId;

    public Product() {
    }

    public Product(int id, String name, String description, Double price, int categoryId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
    }

    public Product(int id, String name, String description, int categoryId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", categoryId=" + categoryId +
                '}';
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
