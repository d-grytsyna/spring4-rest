package com.kpi.springboot_laba_2.service.impl;

import com.kpi.springboot_laba_2.dto.CategoryDTO;
import com.kpi.springboot_laba_2.dto.ProductDTO;
import com.kpi.springboot_laba_2.dto.SubCategoryInfoDTO;
import com.kpi.springboot_laba_2.entity.Category;
import com.kpi.springboot_laba_2.entity.Product;
import com.kpi.springboot_laba_2.exception.CouldNotCreateException;
import com.kpi.springboot_laba_2.exception.CouldNotDeleteException;
import com.kpi.springboot_laba_2.exception.DataFormattingException;
import com.kpi.springboot_laba_2.exception.NotFoundException;
import com.kpi.springboot_laba_2.infopage.PageInfo;
import com.kpi.springboot_laba_2.mapper.CategoryMapper;
import com.kpi.springboot_laba_2.mapper.ProductMapper;
import com.kpi.springboot_laba_2.mapper.SubCategoryMapper;
import com.kpi.springboot_laba_2.repository.CategoryRepository;
import com.kpi.springboot_laba_2.service.CategoryService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryDTO getCategoryDTOById(int id) {
        Category category = categoryRepository.findById(id);
        if(category==null){
            throw new NotFoundException("Category with id " +  id + " not found");
        }
        try {
            //Map category
            CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper.class);
            CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);
            categoryDTO = getSubCategoriesAndProducts(categoryDTO);
            return categoryDTO;
        }catch (RuntimeException e){
            throw new DataFormattingException("Category dto creation failed");
        }
    }

    @Override
    public Boolean deleteCategory(int id) {
        Category category = categoryRepository.findById(id);
        if(category==null){
            throw new NotFoundException("Category with provided id not found");
        }
        try {
            categoryRepository.deleteById(id);
            Category category1 = categoryRepository.findById(id);
            return category1==null;
        }catch (RuntimeException e){
            throw new CouldNotDeleteException("Could not delete category");
        }
    }

    @Override
    public Category createCategory(Category category) {
        if(category.getParentId()!=0) {
        Category parentCategory = categoryRepository.findById(category.getParentId());
        if(parentCategory==null) {
            throw new NotFoundException("Parent category with this id does not exist");
        }
        }
        try {
            categoryRepository.save(category);
            Category category1 = categoryRepository.findById(category.getId());
            return category1;
        }catch (RuntimeException e){
            throw new CouldNotCreateException("Could not create category");
        }

    }

    @Override
    public Category updateCategoryName(int id, String name) {
        Category category = categoryRepository.findById(id);
        if(category==null){
            throw new NotFoundException("Category with provided id not found");
        }
        try {
            category.setName(name);
            categoryRepository.save(category);
            return categoryRepository.findById(id);
        }catch (RuntimeException e){
            throw new CouldNotCreateException("Could not update category");
        }
    }

    @Override
    public int createCategoryUniqueId() {
        return categoryRepository.createUniqueId();
    }

    @Override
    public PageInfo<CategoryDTO> getAllCategories(int page, Integer parentId) {
        PageInfo<Category> info = categoryRepository.getAllCategoriesByPage(page, 2, parentId);
        List<Category> categories = info.getObj();
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        if(categories.isEmpty()){
            throw new NotFoundException("Categories not found");
        }
        try {
            for(Category category:categories){
                CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper.class);
                CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);
                categoryDTO = getSubCategoriesAndProducts(categoryDTO);
                categoryDTOList.add(categoryDTO);
            }
            PageInfo<CategoryDTO> dtoPageInfo = new PageInfo<>(categoryDTOList, info.getTotalPages(), info.getTotalRecords(), info.getRecordsPerPage());
            return dtoPageInfo;
        }catch (RuntimeException e){
            throw new DataFormattingException("Could not create the dto");
        }

    }

    private CategoryDTO getSubCategoriesAndProducts(CategoryDTO categoryDTO) throws RuntimeException{
        List<Category> subCategories =  categoryRepository.findAllSubCategories(categoryDTO.getId());
        List<Product> products = categoryRepository.findAllSubProducts(categoryDTO.getId());
        //Map sub-categories
        if(!subCategories.isEmpty()){
            SubCategoryMapper subCategoryMapper = Mappers.getMapper(SubCategoryMapper.class);
            List<SubCategoryInfoDTO> subCategoryInfoDTOS = subCategoryMapper.subCategoriesToSubCategoriesDTOMapper(subCategories);
            categoryDTO.setSubCategories(subCategoryInfoDTOS);
        }

        //Map products
        if(!products.isEmpty()){
            ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);
            List<ProductDTO> productDTOS = productMapper.productsToProductDTO(products);
            categoryDTO.setProducts(productDTOS);
        }
        return categoryDTO;

    }
}
