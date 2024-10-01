package com.example.demo.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class ProductResponseDTO implements Serializable {
    private String name;
    private String description;
    private Double price;
    private Long quantity;
}