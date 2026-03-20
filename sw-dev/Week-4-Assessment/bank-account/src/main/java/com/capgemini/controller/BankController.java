package com.capgemini.controller;

import com.capgemini.model.entity.Bank;
import com.capgemini.model.service.BankService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/accounts")
public class BankController {

    @Autowired
    private BankService service;

    // CREATE
    @PostMapping
    public Object createAccount(@RequestBody Bank account) {
        return service.createAccount(account);
    }

    // GET BY ID
    @GetMapping("/{id}")
    public Object getAccount(@PathVariable int id) {
        return service.getAccount(id);
    }

    // GET ALL
    @GetMapping
    public Object getAllAccounts() {
        return service.getAllAccounts();
    }

    // UPDATE
    @PutMapping("/{id}")
    public Object updateAccount(@PathVariable int id, @RequestBody Bank account) {
        return service.updateAccount(id, account);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public Object deleteAccount(@PathVariable int id) {
        return service.deleteAccount(id);
    }

    // DEPOSIT
    @PostMapping("/deposit")
    public Object deposit(@RequestBody Map<String, Integer> request) {
        return service.deposit(request.get("accountId"), request.get("amount"));
    }

    // WITHDRAW
    @PostMapping("/withdraw")
    public Object withdraw(@RequestBody Map<String, Integer> request) {
        return service.withdraw(request.get("accountId"), request.get("amount"));
    }
}