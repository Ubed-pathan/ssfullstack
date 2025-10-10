package com.ssbackend.ssbackend.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ssbackend.ssbackend.entity.Customer;

public interface CustomerService {
    Customer create(Customer c);
    Customer update(Long id, Customer c);
    void delete(Long id);
    Optional<Customer> get(Long id);
    Page<Customer> list(String q, Pageable pageable);
    java.util.List<Customer> findAll();
}
