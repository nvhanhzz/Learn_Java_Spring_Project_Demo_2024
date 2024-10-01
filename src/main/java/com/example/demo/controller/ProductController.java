package com.example.demo.controller;

import com.example.demo.dto.request.SaveProductRequestDTO;
import com.example.demo.dto.request.UpdateProductRequestDTO;
import com.example.demo.dto.response.ProductResponseDTO;
import com.example.demo.dto.response.ResponseData;
import com.example.demo.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Tag(name = "Product controller", description = "APIs for managing products")
public class ProductController {
    private final ProductService productService;

    @Operation(
            method = "GET",
            summary = "Get a product by ID",
            description = "Retrieve a product's details by providing the product ID"
    )
    @GetMapping("/{productId}")
    public ResponseData<ProductResponseDTO> getProduct(@PathVariable long productId) {
        return new ResponseData<>(HttpStatus.OK.value(), "Get product successfully", productService.getProduct(productId));
    }

    @Operation(
            method = "GET",
            summary = "Get all product",
            description = "Retrieve all product's details"
    )
    @GetMapping("/all")
    public ResponseData<List<ProductResponseDTO>> getAllProduct() {
        return new ResponseData<>(HttpStatus.OK.value(), "Get products successfully", productService.getAllProducts());
    }

    @Operation(
            method = "POST",
            summary = "Save a product",
            description = "Save a product"
    )
    @PostMapping("")
    public ResponseData<Long> saveProduct(@RequestBody @Valid SaveProductRequestDTO saveProductRequestDTO) {
        return new ResponseData<>(HttpStatus.OK.value(), "Save product successfully", productService.saveProduct(saveProductRequestDTO));
    }

    @Operation(
            method = "PATCH",
            summary = "Update a product",
            description = "Update a product"
    )
    @PatchMapping("/{productId}")
    public ResponseData<?> updateProduct(@PathVariable long productId, @RequestBody @Valid UpdateProductRequestDTO updateProductRequestDTO) {
        productService.updateProduct(productId, updateProductRequestDTO);
        return new ResponseData<>(HttpStatus.OK.value(), "Update product successfully");
    }

    @Operation(
            method = "DELETE",
            summary = "Delete a product",
            description = "Delete a product"
    )
    @DeleteMapping("/{productId}")
    public ResponseData<?> updateProduct(@PathVariable long productId) {
        productService.deleteProduct(productId);
        return new ResponseData<>(HttpStatus.OK.value(), "Delete product successfully");
    }
}