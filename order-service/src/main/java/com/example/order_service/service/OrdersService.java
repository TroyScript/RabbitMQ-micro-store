package com.example.order_service.service;

import com.example.order_service.dto.OrderMessageDTO;
import com.example.order_service.dto.OrderRequestDTO;
import com.example.order_service.dto.OrderResponseDTO;
import com.example.order_service.dto.OrderStatus;
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

    public OrderResponseDTO sendOrder(OrderRequestDTO orderRequest){
        OrderMessageDTO orderMessage = new OrderMessageDTO();
        orderMessage.setProductName(orderRequest.getProductName());
        orderMessage.setQuantity(orderRequest.getQuantity());
        orderMessage.setOrderStatus(OrderStatus.PENDING);

        rabbitTemplate.convertAndSend(orderExchangeName, orderPlacedRoutingKey, orderMessage);

        return new OrderResponseDTO(
                orderMessage.getProductName(),
                orderMessage.getQuantity(),
                "Order registered successfully",
                orderMessage.getOrderStatus()
                );
    }
}