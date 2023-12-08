package com.kpi.springboot_laba_2.repository;

import com.kpi.springboot_laba_2.entity.Category;
import com.kpi.springboot_laba_2.entity.Product;
import com.kpi.springboot_laba_2.infopage.PageInfo;

import java.util.List;

public interface CategoryRepository {
    List<Category> findAll();
    List<Category> findAllSubCategories(int id);
    List<Product> findAllSubProducts(int id);
    Category findById(int id);
    void save(Category category);
    void update(int id, Category category);
    void deleteById(int id);
    int createUniqueId();
    PageInfo<Category> getAllCategoriesByPage(int page, int pageSize, Integer parentId);
}
