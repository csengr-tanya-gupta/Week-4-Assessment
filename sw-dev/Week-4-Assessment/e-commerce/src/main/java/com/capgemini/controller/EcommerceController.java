package com.capgemini.controller;

import com.capgemini.model.entity.*;
import com.capgemini.model.service.EcommerceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EcommerceController {

    @Autowired
    private EcommerceService service;

    // CUSTOMER
    @PostMapping("/customers")
    public Customer create(@RequestBody Customer c) {
        return service.createCustomer(c);
    }

    @GetMapping("/customers")
    public List<Customer> getAll() {
        return service.getAllCustomers();
    }

    @DeleteMapping("/customers/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteCustomer(id);
    }

    @GetMapping("/customers/{id}/restore")
    public void restore(@PathVariable Long id) {
        service.restoreCustomer(id);
    }

    // ORDER
    @PostMapping("/customers/{id}/orders")
    public Order place(@PathVariable Long id, @RequestBody Order o) {
        return service.placeOrder(id, o);
    }

    @GetMapping("/customers/{id}/orders")
    public Page<Order> getOrders(
            @PathVariable Long id,
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        return service.getOrders(id, status, page, size);
    }

    @PatchMapping("/customers/{id}/orders/{orderId}/cancel")
    public Order cancel(@PathVariable Long orderId) {
        return service.cancelOrder(orderId);
    }

    // REVENUE
    @GetMapping("/customers/revenue-summary")
    public List<?> revenue() {
        return service.revenue();
    }
}