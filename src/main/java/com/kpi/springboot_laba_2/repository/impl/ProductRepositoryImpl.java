package com.kpi.springboot_laba_2.repository.impl;

import com.kpi.springboot_laba_2.entity.Product;
import com.kpi.springboot_laba_2.infopage.PageInfo;
import com.kpi.springboot_laba_2.repository.ProductRepository;
import com.kpi.springboot_laba_2.util.ProductDB;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private ProductDB productDB;

    public ProductRepositoryImpl(ProductDB productDB) {
        this.productDB = productDB;
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(productDB.getProductDB().values());
    }

    @Override
    public List<Product> findAllProductsByCategoryId(int id) {
        List<Product> products = findAll();
        products.removeIf(el -> el.getCategoryId() != id);
        return products;
    }

    @Override
    public Product findById(int id) {
        return productDB.getProductDB().get(id);
    }

    @Override
    public void save(Product product) {
        productDB.getProductDB().put(product.getId(), product);
    }

    @Override
    public void deleteById(int id) {
        productDB.getProductDB().remove(id);
    }

    @Override
    public void update(int id, Product product) {
        productDB.getProductDB().replace(id, product);
    }

    @Override
    public int createUniqueId() {
        return productDB.createUniqueId();
    }


    @Override
    public PageInfo<Product> getAllProductsByPage(int page, int pageSize, Double maxPrice) {
        List<Product> allProducts = new ArrayList<>(productDB.getProductDB().values());
        if(maxPrice!=null){
            List<Product> checkedProducts = new ArrayList<>();
            for(Product product: allProducts){
                if(product.getPrice()<=maxPrice){
                    checkedProducts.add(product);
                }
            }
            allProducts.clear();
            allProducts.addAll(checkedProducts);
        }
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, allProducts.size());
        int totalPages = (int) Math.ceil((double) allProducts.size() / pageSize);
        int totalRecords = allProducts.size();
        PageInfo<Product>  pageInfo = new PageInfo<>(allProducts.subList(start, end), totalPages, totalRecords, pageSize);
        return  pageInfo;
    }
}
