package com.kpi.springboot_laba_2.mapper;


import com.kpi.springboot_laba_2.dto.ProductDTO;
import com.kpi.springboot_laba_2.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface ProductMapper {
    ProductDTO productToProductDTO(Product product);
    default List<ProductDTO> productsToProductDTO(List<Product> products) {
        List<ProductDTO> productDTOs = new ArrayList<>();
        for (Product product : products) {
            ProductDTO productDTO = productToProductDTO(product);
            productDTOs.add(productDTO);
        }
        return productDTOs;
    }
}
