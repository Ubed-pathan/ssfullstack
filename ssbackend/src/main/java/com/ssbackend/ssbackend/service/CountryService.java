package com.ssbackend.ssbackend.service;

import com.ssbackend.ssbackend.entity.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CountryService {
    Country create(Country c);
    Country update(Long id, Country c);
    void delete(Long id);
    Optional<Country> get(Long id);
    Page<Country> list(String q, Pageable pageable);
}
