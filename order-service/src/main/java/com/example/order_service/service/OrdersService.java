package com.example.order_service.service;

import com.example.order_service.model.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrdersService {
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.name}")
    private String orderExchangeName;

    @Value("${rabbitmq.routing.key}")
    private String orderPlacedRoutingKey;

    public void sendOrder(OrderRequest order){
        rabbitTemplate.convertAndSend(orderExchangeName, orderPlacedRoutingKey, order);
    }
}