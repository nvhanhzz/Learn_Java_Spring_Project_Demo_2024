package com.example.demo.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class UpdateProductRequestDTO implements Serializable {
    private String name;

    private String description;

    private Double price;

    @Min(value = 0, message = "Quantity must be greater than or equal to 0")
    private Long quantity;
}