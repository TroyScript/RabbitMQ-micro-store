package com.example.inventory_service.service;

import com.example.inventory_service.dto.OrderRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class InventoryService {
    // Simulate a stock
    private final Map<String, Integer> stockHashMap = new HashMap<>(Map.of(
            "Keyboard", 5
    ));

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void processOrder(OrderRequestDTO orderRequestDTO) {
        var orderedProductName = orderRequestDTO.getProductName();
        if (!stockHashMap.containsKey(orderedProductName)) {
            return;
        }

        var orderedQuantity = orderRequestDTO.getQuantity();
        var stockBefore = stockHashMap.get(orderedProductName);

        if (stockBefore < orderedQuantity) {
            return;
        }

        var stockAfter = stockBefore - orderedQuantity;

        stockHashMap.put(orderedProductName, stockAfter);
        log.info("{} product(s) shipped. New '{}' amount: {}", orderedQuantity, orderedProductName, stockAfter);
    }
}

