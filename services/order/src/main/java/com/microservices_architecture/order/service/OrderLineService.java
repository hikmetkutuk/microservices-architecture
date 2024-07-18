package com.microservices_architecture.order.service;

import com.microservices_architecture.order.dto.OrderLineRequest;
import com.microservices_architecture.order.mapper.OrderLineMapper;
import com.microservices_architecture.order.repository.OrderLineRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderLineService {

    private final OrderLineRepository orderLineRepository;
    private final OrderLineMapper mapper;

    public OrderLineService(OrderLineRepository orderLineRepository, OrderLineMapper mapper) {
        this.orderLineRepository = orderLineRepository;
        this.mapper = mapper;
    }

    public int createOrderLine(OrderLineRequest orderLineRequest) {
        var orderLine = mapper.toOrderLine(orderLineRequest);
        return orderLineRepository.save(orderLine).getId();
    }
}
