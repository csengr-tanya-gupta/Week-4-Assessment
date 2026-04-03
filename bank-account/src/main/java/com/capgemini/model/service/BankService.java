package com.capgemini.model.service;

import com.capgemini.model.entity.Bank;
import com.capgemini.model.repository.BankRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BankService {

    @Autowired
    private BankRepository repository;

    // CREATE
    public Map<String, Object> createAccount(Bank account) {

        if (account.getBalance() < 0) {
            return Map.of("message", "Balance cannot be negative");
        }

        Bank saved = repository.save(account);

        return Map.of(
                "message", "Account created successfully",
                "accountId", saved.getId()
        );
    }

    // GET BY ID
    public Object getAccount(int id) {
        Optional<Bank> optional = repository.findById(id);

        if (optional.isEmpty()) {
            return Map.of("message", "Account not found");
        }

        return optional.get();
    }

    // GET ALL
    public List<Bank> getAllAccounts() {
        return repository.findAll();
    }

    // UPDATE
    public Map<String, String> updateAccount(int id, Bank updated) {

        Optional<Bank> optional = repository.findById(id);

        if (optional.isEmpty()) {
            return Map.of("message", "Account not found");
        }

        if (updated.getBalance() < 0) {
            return Map.of("message", "Balance cannot be negative");
        }

        Bank existing = optional.get();
        existing.setAccountHolder(updated.getAccountHolder());
        existing.setBalance(updated.getBalance());

        repository.save(existing);

        return Map.of("message", "Account updated successfully");
    }

    // DELETE
    public Map<String, String> deleteAccount(int id) {

        Optional<Bank> optional = repository.findById(id);

        if (optional.isEmpty()) {
            return Map.of("message", "Account not found");
        }

        repository.deleteById(id);

        return Map.of("message", "Account deleted successfully");
    }

    // DEPOSIT
    public Map<String, Object> deposit(int accountId, int amount) {

        Optional<Bank> optional = repository.findById(accountId);

        if (optional.isEmpty()) {
            return Map.of("message", "Account not found");
        }

        if (amount <= 0) {
            return Map.of("message", "Invalid amount");
        }

        Bank account = optional.get();
        account.setBalance(account.getBalance() + amount);

        repository.save(account);

        return Map.of(
                "message", "Deposit successful",
                "updatedBalance", account.getBalance()
        );
    }

    // WITHDRAW
    public Map<String, Object> withdraw(int accountId, int amount) {

        Optional<Bank> optional = repository.findById(accountId);

        if (optional.isEmpty()) {
            return Map.of("message", "Account not found");
        }

        if (amount <= 0) {
            return Map.of("message", "Invalid amount");
        }

        Bank account = optional.get();

        if (account.getBalance() < amount) {
            return Map.of("message", "Insufficient balance");
        }

        account.setBalance(account.getBalance() - amount);

        repository.save(account);

        return Map.of(
                "message", "Withdrawal successful",
                "updatedBalance", account.getBalance()
        );
    }
}