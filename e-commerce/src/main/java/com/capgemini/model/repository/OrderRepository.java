package com.capgemini.model.repository;

import com.capgemini.model.entity.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findByCustomerCustomerIdAndStatus(
            Long customerId, OrderStatus status, Pageable pageable);

    Page<Order> findByCustomerCustomerId(
            Long customerId, Pageable pageable);

    @Query("""
        SELECT o.customer.fullName, SUM(o.totalAmount)
        FROM Order o
        WHERE o.status = 'DELIVERED'
        GROUP BY o.customer.fullName
    """)
    List<Object[]> revenueSummary();
}