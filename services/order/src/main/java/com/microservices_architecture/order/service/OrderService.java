package com.microservices_architecture.order.service;

import com.microservices_architecture.order.client.CustomerClient;
import com.microservices_architecture.order.client.ProductClient;
import com.microservices_architecture.order.dto.OrderLineRequest;
import com.microservices_architecture.order.dto.OrderRequest;
import com.microservices_architecture.order.dto.OrderResponse;
import com.microservices_architecture.order.exception.BusinessException;
import com.microservices_architecture.order.mapper.OrderMapper;
import com.microservices_architecture.order.repository.OrderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository orderRepository;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;

    public OrderService(CustomerClient customerClient, ProductClient productClient, OrderRepository orderRepository, OrderMapper mapper, OrderLineService orderLineService) {
        this.customerClient = customerClient;
        this.productClient = productClient;
        this.orderRepository = orderRepository;
        this.mapper = mapper;
        this.orderLineService = orderLineService;
    }

    public ResponseEntity<OrderResponse> createOrder(OrderRequest orderRequest) {
        // check if customer exists (openfeign)
        var customer = customerClient.getCustomerById(orderRequest.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order:: No customer exists with the provided id"));

        // purchase products
        productClient.purchaseProducts(orderRequest.products())
                .orElseThrow(() -> new BusinessException("Cannot create order:: An error occurred while purchasing products"));

        // persist order
        var order = orderRepository.save(mapper.toOrder(orderRequest));

        // persist order lines
        for (var purchaseRequest : orderRequest.products()) {
            orderLineService.createOrderLine(
                    new OrderLineRequest(
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }

        return ResponseEntity.ok(null);
    }
}
