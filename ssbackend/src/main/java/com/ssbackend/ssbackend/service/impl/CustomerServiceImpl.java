package com.ssbackend.ssbackend.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssbackend.ssbackend.controller.ResourceNotFoundException;
import com.ssbackend.ssbackend.entity.Customer;
import com.ssbackend.ssbackend.repository.CustomerRepository;
import com.ssbackend.ssbackend.service.CustomerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository repo;

    @Override
    public Customer create(Customer c) { return repo.save(c); }

    @Override
    public Customer update(Long id, Customer c) {
        Customer existing = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + id));
        existing.setName(c.getName());
        existing.setEmail(c.getEmail());
        existing.setMobile(c.getMobile());
        existing.setImageUrl(c.getImageUrl());
        return repo.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new ResourceNotFoundException("Customer not found: " + id);
        repo.deleteById(id);
    }

    @Override @Transactional(readOnly = true)
    public Optional<Customer> get(Long id) { return repo.findById(id); }

    @Override @Transactional(readOnly = true)
    public Page<Customer> list(String q, Pageable pageable) {
        Pageable p = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
        return repo.findAll(p);
    }

    @Override @Transactional(readOnly = true)
    public java.util.List<Customer> findAll() {
        return repo.findAll(Sort.by("id").ascending());
    }
}
