package com.example.order_service.dto;

import lombok.Data;

@Data
public class OrderRequestDTO {
    private int id;
    private int quantity;
    private String productName;
}