package com.example.inventory_service.service;

import com.example.inventory_service.dto.OrderMessageDTO;
import com.example.inventory_service.dto.OrderStatus;
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

    // Would need to tell something to the client, but I'm not going to do that here
    // as it is a simulated ambient and not the objective of this project.
    // A way to do that is by creating another queues for order confirmation and cancellation.
    private void confirmOrder(OrderMessageDTO orderMessage) {
        orderMessage.setOrderStatus(OrderStatus.CONFIRMED);
        log.info("Order confirmed: {}", orderMessage);
    }

    private void cancelOrder(OrderMessageDTO orderMessage) {
        orderMessage.setOrderStatus(OrderStatus.CANCELLED);
        log.info("Order cancelled: {}", orderMessage);
    }

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void processOrder(OrderMessageDTO orderMessage) {
        var orderedProductName = orderMessage.getProductName();
        if (!stockHashMap.containsKey(orderedProductName)) {
            cancelOrder(orderMessage);
            return;
        }

        var orderedQuantity = orderMessage.getQuantity();
        var stockBefore = stockHashMap.get(orderedProductName);

        if (stockBefore < orderedQuantity) {
            cancelOrder(orderMessage);
            return;
        }

        var stockAfter = stockBefore - orderedQuantity;

        stockHashMap.put(orderedProductName, stockAfter);
        confirmOrder(orderMessage);

        log.info("{} product(s) shipped. New '{}' amount: {}", orderedQuantity, orderedProductName, stockAfter);
    }
}