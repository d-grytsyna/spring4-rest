package com.kpi.springboot_laba_2.service;

import com.kpi.springboot_laba_2.entity.Category;
import com.kpi.springboot_laba_2.entity.Product;

import java.util.List;

public interface AppMVCService {
    List<Category> findAllCategories();
    Category findCategoryById(int id);
    void createCategory(Category category);
    void updateCategory(int id, Category category);
    List<Product> findAllProducts();
    List<Product> findAllProductsByCategoryId(int id);
    Product findProductById(int id);
    void createProduct(Product product);
    void updateProduct(int id, Product product);
    void deleteProduct(int id);
    void deleteCategory(int id);
    int createProductUniqueId();
    int createCategoryUniqueId();

}
