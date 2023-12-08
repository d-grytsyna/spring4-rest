package com.kpi.springboot_laba_2.repository.impl;

import com.kpi.springboot_laba_2.entity.Category;
import com.kpi.springboot_laba_2.entity.Product;
import com.kpi.springboot_laba_2.infopage.PageInfo;
import com.kpi.springboot_laba_2.repository.CategoryRepository;
import com.kpi.springboot_laba_2.repository.ProductRepository;
import com.kpi.springboot_laba_2.util.CategoryDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {
    private ProductRepository productRepository;

    private CategoryDB categoryDB;

    @Autowired
    public void setCategoryDB(CategoryDB categoryDB){
        this.categoryDB = categoryDB;
    }

    @Autowired
    public void setProductRepository(ProductRepository productRepository){
        this.productRepository = productRepository;
    }


    @Override
    public List<Category> findAll() {
        return new ArrayList<>(categoryDB.getCategoryDB().values());
    }

    @Override
    public List<Category> findAllSubCategories(int id) {
        List<Category> categories = findAll();
        categories.removeIf(el -> el.getParentId() != id);
        return categories;
    }

    @Override
    public List<Product> findAllSubProducts(int id) {
        return productRepository.findAllProductsByCategoryId(id);
    }

    @Override
    public Category findById(int id) {
        return categoryDB.getCategoryDB().get(id);
    }

    @Override
    public void save(Category category) {
        categoryDB.getCategoryDB().put(category.getId(), category);
    }

    @Override
    public void update(int id, Category category) {
        categoryDB.getCategoryDB().replace(id, category);
    }

    @Override
    public void deleteById(int id) {
        List<Product> subProducts = findAllSubProducts(id);
        subProducts.forEach(el -> {
            productRepository.deleteById(el.getId());
        });

        List<Category> subCategories = findAllSubCategories(id);
        subCategories.forEach(el -> {
            deleteById(el.getId());
        });
        categoryDB.getCategoryDB().remove(id);

    }

    @Override
    public int createUniqueId() {
        return categoryDB.createUniqueId();
    }

    @Override
    public PageInfo<Category> getAllCategoriesByPage(int page, int pageSize, Integer parentId) {
        List<Category> allCategories = new ArrayList<>(categoryDB.getCategoryDB().values());
        if(parentId!=null){
            List<Category> checkedCategories = new ArrayList<>();
            for(Category category: allCategories){
                if(category.getParentId()==parentId){
                    checkedCategories.add(category);
                }
            }
            allCategories.clear();
            allCategories.addAll(checkedCategories);
        }
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, allCategories.size());
        int totalPages = (int) Math.ceil((double) allCategories.size() / pageSize);
        int totalRecords = allCategories.size();
        PageInfo<Category> pageInfo = new PageInfo<>(allCategories.subList(start, end), totalPages, totalRecords, pageSize);
        return pageInfo;
    }
}
