package com.kpi.springboot_laba_2.service.impl;

import com.kpi.springboot_laba_2.dto.ProductDTO;
import com.kpi.springboot_laba_2.entity.Category;
import com.kpi.springboot_laba_2.entity.Product;
import com.kpi.springboot_laba_2.exception.CouldNotCreateException;
import com.kpi.springboot_laba_2.exception.CouldNotDeleteException;
import com.kpi.springboot_laba_2.exception.DataFormattingException;
import com.kpi.springboot_laba_2.exception.NotFoundException;
import com.kpi.springboot_laba_2.form.CreateProductForm;
import com.kpi.springboot_laba_2.form.UpdateProductForm;
import com.kpi.springboot_laba_2.infopage.PageInfo;
import com.kpi.springboot_laba_2.mapper.ProductMapper;
import com.kpi.springboot_laba_2.repository.CategoryRepository;
import com.kpi.springboot_laba_2.repository.ProductRepository;
import com.kpi.springboot_laba_2.service.ProductService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ProductDTO getProductById(int id) {
        Product product = productRepository.findById(id);
        if(product==null){
            throw new NotFoundException("Product with id " +  id + " not found");
        }
        try {
            ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);
            ProductDTO productDTO = productMapper.productToProductDTO(product);
            return productDTO;

        }catch (RuntimeException e){
            throw new DataFormattingException("Product dto creation failed");
        }
    }

    @Override
    public Boolean deleteProduct(int id) {
        Product product = productRepository.findById(id);
        if(product==null){
            throw new NotFoundException("Product with provided id not found");
        }
        try {
            productRepository.deleteById(id);
            Product product1 = productRepository.findById(id);
            return product1==null;
        }catch (RuntimeException e){
            throw new CouldNotDeleteException("Could not delete product");
        }
    }

    @Override
    public int createProductUniqueId() {
        return productRepository.createUniqueId();
    }

    @Override
    public Product createProduct(Product product) {
        Category parentCategory = categoryRepository.findById(product.getCategoryId());
        if(parentCategory==null){
            throw new NotFoundException("Category with this id does not exist");
        }
        try {
            productRepository.save(product);
            Product product1 = productRepository.findById(product.getId());
            return product1;
        }catch (RuntimeException e){
            throw new CouldNotCreateException("Could not create product");
        }
    }

    @Override
    public Product updateProduct(int id, UpdateProductForm updateProductForm) {
        Product product = productRepository.findById(id);
        if(product==null){
            throw new NotFoundException("Product with provided id not found");
        }
        Integer newCategoryId = updateProductForm.getCategoryId();
        if(newCategoryId!=null){
            Category newParentCategory = categoryRepository.findById(newCategoryId);
            if(newParentCategory==null) throw new NotFoundException("Parent category with provided id not found");
        }
        try {
            product.setName(Optional.ofNullable(updateProductForm.getName()).orElse(product.getName()));
            product.setDescription(Optional.ofNullable(updateProductForm.getDescription()).orElse(product.getDescription()));
            product.setPrice(Optional.ofNullable(updateProductForm.getPrice()).orElse(product.getPrice()));
            product.setCategoryId(Optional.ofNullable(updateProductForm.getCategoryId()).orElse(product.getCategoryId()));
            productRepository.save(product);
            return productRepository.findById(id);
        }catch (RuntimeException e){
            throw new CouldNotCreateException("Could not update product");
        }
    }

    @Override
    public PageInfo<ProductDTO> getAllProducts(int page, Double maxPrice) {
        PageInfo<Product> info = productRepository.getAllProductsByPage(page, 2, maxPrice);
        List<Product> productList = info.getObj();
        if(productList.isEmpty()){
            throw new NotFoundException("Products not found");
        }
        try {
            ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);
            List<ProductDTO> productDTOS = productMapper.productsToProductDTO(productList);
            PageInfo<ProductDTO> dtoPageInfo = new PageInfo<>(productDTOS, info.getTotalPages(), info.getTotalRecords(), info.getRecordsPerPage());
            return dtoPageInfo;
        }catch (Exception e){
            throw new DataFormattingException("Dto formation failed");
        }
    }
}
