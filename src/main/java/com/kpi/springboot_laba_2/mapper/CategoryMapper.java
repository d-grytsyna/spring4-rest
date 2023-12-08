package com.kpi.springboot_laba_2.mapper;

import com.kpi.springboot_laba_2.dto.CategoryDTO;
import com.kpi.springboot_laba_2.entity.Category;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryMapper {
    CategoryDTO categoryToCategoryDTO(Category category);
}
