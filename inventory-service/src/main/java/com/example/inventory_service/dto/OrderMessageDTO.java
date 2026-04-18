package com.example.inventory_service.dto;

import lombok.Data;

@Data
public class OrderMessageDTO {
    private String productName;
    private int quantity;
    private OrderStatus orderStatus;
}