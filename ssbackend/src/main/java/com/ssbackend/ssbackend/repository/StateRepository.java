package com.ssbackend.ssbackend.repository;

import com.ssbackend.ssbackend.entity.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<State, Long> {
    Page<State> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<State> findByCountry_Id(Long countryId, Pageable pageable);
}
