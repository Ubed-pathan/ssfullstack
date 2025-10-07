package com.ssbackend.ssbackend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ssbackend.ssbackend.entity.District;

public interface DistrictRepository extends JpaRepository<District, Long> {
    Page<District> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<District> findByStateContainingIgnoreCase(String state, Pageable pageable);
}
