package com.capgemini.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.model.entity.Bank;

public interface BankRepository extends JpaRepository<Bank, Integer>{

}
