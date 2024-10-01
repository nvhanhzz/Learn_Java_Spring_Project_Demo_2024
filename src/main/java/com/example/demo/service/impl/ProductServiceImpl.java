package com.example.demo.service.impl;

import com.example.demo.dto.request.SaveProductRequestDTO;
import com.example.demo.dto.request.UpdateProductRequestDTO;
import com.example.demo.dto.response.ProductResponseDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    private Product getProductById(long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    @Override
    public ProductResponseDTO getProduct(long id) {
        Product product = getProductById(id);
        System.out.println(productMapper.toProductResponseDTO(product));
        return productMapper.toProductResponseDTO(product);
    }

    @Override
    public Page<ProductResponseDTO> getProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Product> productPage = productRepository.findAll(pageable);

        return productPage.map(productMapper::toProductResponseDTO);
    }

    @Override
    public long saveProduct(SaveProductRequestDTO saveProductRequestDTO) {
        Product product = Product.builder()
                .name(saveProductRequestDTO.getName())
                .description(saveProductRequestDTO.getDescription())
                .price(saveProductRequestDTO.getPrice())
                .quantity(saveProductRequestDTO.getQuantity())
                .build();

        productRepository.save(product);

        return product.getId();
    }

    @Override
    public void updateProduct(long id, UpdateProductRequestDTO updateProductRequestDTO) {
        Product product = getProductById(id);

        if (product == null) {
            throw new ResourceNotFoundException("Product not found");
        }

        if (updateProductRequestDTO.getName() != null) {
            product.setName(updateProductRequestDTO.getName());
        }
        if (updateProductRequestDTO.getDescription() != null) {
            product.setDescription(updateProductRequestDTO.getDescription());
        }
        if (updateProductRequestDTO.getPrice() != null) {
            product.setPrice(updateProductRequestDTO.getPrice());
        }
        if (updateProductRequestDTO.getQuantity() != null) {
            product.setQuantity(updateProductRequestDTO.getQuantity());
        }
        productRepository.save(product);
    }

    @Override
    public void deleteProduct(long id) {
        Product product = getProductById(id);

        if (product == null) {
            throw new ResourceNotFoundException("Product not found");
        }

        productRepository.delete(product);
    }
}