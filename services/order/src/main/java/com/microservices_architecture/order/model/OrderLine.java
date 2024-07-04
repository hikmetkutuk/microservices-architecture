package com.microservices_architecture.order.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderLine {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private int productId;
    private double quantity;
}
