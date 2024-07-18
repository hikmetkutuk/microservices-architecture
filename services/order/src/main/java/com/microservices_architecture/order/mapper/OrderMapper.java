package com.microservices_architecture.order.mapper;

import com.microservices_architecture.order.dto.OrderRequest;
import com.microservices_architecture.order.model.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper {
    public Order toOrder(OrderRequest orderRequest) {
        if (orderRequest == null) {
            return null;
        }
        return Order.builder()
                .reference(orderRequest.reference())
                .paymentMethod(orderRequest.paymentMethod())
                .customerId(orderRequest.customerId())
                .reference(orderRequest.reference())
                .totalAmount(orderRequest.totalAmount())
                .build();
    }
}
