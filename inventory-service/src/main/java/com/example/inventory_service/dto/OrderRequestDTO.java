package com.example.inventory_service.dto;

import lombok.Data;

@Data
public class OrderRequestDTO {
    private String productName;
    private int quantity;
}