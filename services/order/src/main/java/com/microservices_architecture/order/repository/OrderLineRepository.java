package com.microservices_architecture.order.repository;

import com.microservices_architecture.order.model.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLineRepository extends JpaRepository<OrderLine, Integer> {
}
