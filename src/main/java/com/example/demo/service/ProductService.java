package com.example.demo.service;

import com.example.demo.dto.request.SaveProductRequestDTO;
import com.example.demo.dto.request.UpdateProductRequestDTO;
import com.example.demo.dto.response.ProductResponseDTO;
import org.springframework.data.domain.Page;

public interface ProductService {
    ProductResponseDTO getProduct(long id);

    Page<ProductResponseDTO> getProducts(int page, int size);

    long saveProduct(SaveProductRequestDTO saveProductRequestDTO);

    void updateProduct(long id, UpdateProductRequestDTO updateProductRequestDTO);

    void deleteProduct(long id);
}