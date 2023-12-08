package com.kpi.springboot_laba_2.service;

import com.kpi.springboot_laba_2.dto.CategoryDTO;
import com.kpi.springboot_laba_2.entity.Category;
import com.kpi.springboot_laba_2.infopage.PageInfo;

import java.util.List;

public interface CategoryService {
    CategoryDTO getCategoryDTOById(int id);
    Boolean deleteCategory(int id);
    Category createCategory(Category category);
    Category updateCategoryName(int id, String name);
    int createCategoryUniqueId();
    PageInfo<CategoryDTO> getAllCategories(int page, Integer parentId);
}
