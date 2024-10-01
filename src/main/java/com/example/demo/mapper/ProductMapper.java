package com.example.demo.mapper;

import com.example.demo.dto.response.ProductResponseDTO;
import com.example.demo.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "quantity", source = "quantity")
    ProductResponseDTO toProductResponseDTO(Product product);
}