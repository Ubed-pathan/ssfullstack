package com.ssbackend.ssbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssbackend.ssbackend.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
