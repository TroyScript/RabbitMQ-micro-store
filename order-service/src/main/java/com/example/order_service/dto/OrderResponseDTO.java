package com.example.order_service.dto;

public record OrderResponseDTO (
    String productName,
    int quantity,
    String message,
    OrderStatus orderStatus
) {}