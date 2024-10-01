package com.example.demo.service;

import com.example.demo.dto.request.SaveProductRequestDTO;
import com.example.demo.dto.request.UpdateProductRequestDTO;
import com.example.demo.dto.response.ProductResponseDTO;

import java.util.List;

public interface ProductService {
    ProductResponseDTO getProduct(long id);

    List<ProductResponseDTO> getAllProducts();

    long saveProduct(SaveProductRequestDTO saveProductRequestDTO);

    void updateProduct(long id, UpdateProductRequestDTO updateProductRequestDTO);

    void deleteProduct(long id);
}