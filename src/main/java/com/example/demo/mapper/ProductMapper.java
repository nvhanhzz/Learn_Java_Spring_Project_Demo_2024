package com.example.demo.mapper;

import com.example.demo.dto.response.ProductResponseDTO;
import com.example.demo.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponseDTO toProductResponseDTO(Product product);
}