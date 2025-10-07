package com.ssbackend.ssbackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ssbackend.ssbackend.entity.State;

public interface StateRepository extends JpaRepository<State, Long> {
    Page<State> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<State> findByCountryContainingIgnoreCase(String country, Pageable pageable);
}
