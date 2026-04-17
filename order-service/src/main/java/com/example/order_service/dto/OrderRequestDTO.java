package com.example.order_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OrderRequestDTO {
    @NotBlank(message = "Product must be specified")
    private String productName;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;
}