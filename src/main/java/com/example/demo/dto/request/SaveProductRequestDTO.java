package com.example.demo.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SaveProductRequestDTO implements Serializable {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Price is required")
    private Double price;

    @NotNull
    @Min(value = 0, message = "Quantity must be greater than or equal to 0")
    private Long quantity;
}