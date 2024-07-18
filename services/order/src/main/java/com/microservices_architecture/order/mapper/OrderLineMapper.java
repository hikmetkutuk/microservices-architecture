package com.microservices_architecture.order.mapper;

import com.microservices_architecture.order.dto.OrderLineRequest;
import com.microservices_architecture.order.model.Order;
import com.microservices_architecture.order.model.OrderLine;
import org.springframework.stereotype.Service;

@Service
public class OrderLineMapper {

    public OrderLine toOrderLine(OrderLineRequest orderLineRequest) {
        return OrderLine.builder()
                .productId(orderLineRequest.productId())
                .quantity(orderLineRequest.quantity())
                .order(
                        Order
                                .builder()
                                .id(orderLineRequest.orderId())
                                .build()
                )
                .build();
    }
}
