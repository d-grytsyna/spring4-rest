package com.kpi.springboot_laba_2.service;

import com.kpi.springboot_laba_2.dto.CategoryDTO;
import com.kpi.springboot_laba_2.dto.ProductDTO;
import com.kpi.springboot_laba_2.entity.Product;
import com.kpi.springboot_laba_2.form.CreateProductForm;
import com.kpi.springboot_laba_2.form.UpdateProductForm;
import com.kpi.springboot_laba_2.infopage.PageInfo;

import java.util.List;


public interface ProductService {
    ProductDTO getProductById(int id);
    Boolean deleteProduct(int id);
    int createProductUniqueId();
    Product createProduct(Product product);
    Product updateProduct(int id, UpdateProductForm updateProductForm);
    PageInfo<ProductDTO> getAllProducts(int page, Double maxPrice);
}
