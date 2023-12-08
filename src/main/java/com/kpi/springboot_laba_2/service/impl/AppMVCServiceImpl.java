package com.kpi.springboot_laba_2.service.impl;

import com.kpi.springboot_laba_2.entity.Category;
import com.kpi.springboot_laba_2.entity.Product;
import com.kpi.springboot_laba_2.repository.CategoryRepository;
import com.kpi.springboot_laba_2.repository.ProductRepository;
import com.kpi.springboot_laba_2.service.AppMVCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppMVCServiceImpl implements AppMVCService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findCategoryById(int id) {
        return categoryRepository.findById(id);
    }



    @Override
    public void updateCategory(int id, Category category) {
        categoryRepository.update(id, category);
    }



    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> findAllProductsByCategoryId(int id) {
        return productRepository.findAllProductsByCategoryId(id);
    }

    @Override
    public Product findProductById(int id) {
        return productRepository.findById(id);
    }

    @Override
    public void createCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public void createProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void updateProduct(int id, Product product) {
        productRepository.update(id, product);
    }

    @Override
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }
    @Override
    public void deleteCategory(int id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public int createProductUniqueId() {
        return productRepository.createUniqueId();
    }

    @Override
    public int createCategoryUniqueId() {
        return categoryRepository.createUniqueId();
    }



}
