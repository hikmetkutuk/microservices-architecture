package com.microservices.orderservice.service;

import com.microservices.orderservice.dto.OrderLineItemsResponse;
import com.microservices.orderservice.dto.OrderRequest;
import com.microservices.orderservice.model.Order;
import com.microservices.orderservice.model.OrderLineItems;
import com.microservices.orderservice.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public String placeOrder(OrderRequest orderRequest) {
        try {
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());

            List<OrderLineItems> orderLineItems = orderRequest.orderLineItemsList()
                    .stream()
                    .map(this::mapToDto)
                    .toList();

            order.setOrderLineItemsList(orderLineItems);

            orderRepository.save(order);
            String message = "Order placed successfully";
            log.info(message);
            return message;
        } catch (DataAccessException e) {
            log.error("Failed to place order: {}", e.getMessage());
            throw new RuntimeException("Failed to place order", e);
        }
    }

    private OrderLineItems mapToDto(OrderLineItemsResponse orderLineItemsResponse) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setSkuCode(orderLineItemsResponse.skuCode());
        orderLineItems.setPrice(orderLineItemsResponse.price());
        orderLineItems.setQuantity(orderLineItemsResponse.quantity());
        return orderLineItems;
    }
}
