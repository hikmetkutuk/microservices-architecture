package com.microservices_architecture.order.dto;

import com.microservices_architecture.order.model.OrderLine;
import com.microservices_architecture.order.model.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;

public record OrderResponse(
        String reference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        String customerId,
        List<OrderLine> orderLines
) {
}
