package com.ssbackend.ssbackend.repository;

import com.ssbackend.ssbackend.entity.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
    boolean existsByNameIgnoreCase(String name);
    Page<Country> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
