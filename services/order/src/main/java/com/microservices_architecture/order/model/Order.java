package com.microservices_architecture.order.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Order {

    @Id
    @GeneratedValue
    private int id;
    private String reference;
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    private String customerId;

    @OneToMany(mappedBy = "order")
    private List<OrderLine> orderLines; //<product>

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime updatedAt;
}
