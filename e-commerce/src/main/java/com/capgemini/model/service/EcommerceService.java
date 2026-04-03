package com.capgemini.model.service;

import com.capgemini.exception.*;
import com.capgemini.model.entity.*;
import com.capgemini.model.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class EcommerceService {

    @Autowired private CustomerRepository customerRepo;
    @Autowired private OrderRepository orderRepo;

    // CREATE CUSTOMER
    public Customer createCustomer(Customer c) {
        customerRepo.findByEmail(c.getEmail())
                .ifPresent(x -> { throw new DuplicateException("Email already exists"); });
        return customerRepo.save(c);
    }

    // GET ACTIVE CUSTOMERS
    public List<Customer> getAllCustomers() {
        return customerRepo.findAll().stream()
                .filter(Customer::getIsActive)
                .toList();
    }

    // SOFT DELETE
    public void deleteCustomer(Long id) {
        Customer c = customerRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        c.setIsActive(false);
        customerRepo.save(c);
    }

    // RESTORE
    public void restoreCustomer(Long id) {
        Customer c = customerRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        c.setIsActive(true);
        customerRepo.save(c);
    }

    // PLACE ORDER
    public Order placeOrder(Long customerId, Order order) {

        Customer c = customerRepo.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        if (order.getQuantity() < 1)
            throw new BadRequestException("Quantity must be >=1");

        if (order.getPricePerUnit().compareTo(BigDecimal.valueOf(0.01)) < 0)
            throw new BadRequestException("Invalid price");

        order.setTotalAmount(
                order.getPricePerUnit().multiply(BigDecimal.valueOf(order.getQuantity()))
        );

        order.setCustomer(c);

        return orderRepo.save(order);
    }

    // GET ORDERS (FILTER + PAGINATION)
    public Page<Order> getOrders(Long customerId, OrderStatus status, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        if (status != null)
            return orderRepo.findByCustomerCustomerIdAndStatus(customerId, status, pageable);

        return orderRepo.findByCustomerCustomerId(customerId, pageable);
    }

    // CANCEL ORDER
    public Order cancelOrder(Long orderId) {
        Order o = orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (o.getStatus() == OrderStatus.SHIPPED || o.getStatus() == OrderStatus.DELIVERED)
            throw new BadRequestException("Cannot cancel this order");

        o.setStatus(OrderStatus.CANCELLED);
        return orderRepo.save(o);
    }

    // REVENUE
    public List<Map<String, Object>> revenue() {
        List<Object[]> data = orderRepo.revenueSummary();

        List<Map<String, Object>> result = new ArrayList<>();

        for (Object[] row : data) {
            result.add(Map.of(
                    "customerName", row[0],
                    "totalRevenue", row[1]
            ));
        }

        return result;
    }
}