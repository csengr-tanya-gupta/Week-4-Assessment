package com.capgemini.model.entity;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private String productName;

    private Integer quantity;

    private BigDecimal pricePerUnit;

    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Version
    private Integer version;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}