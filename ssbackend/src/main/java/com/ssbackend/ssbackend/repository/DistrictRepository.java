package com.ssbackend.ssbackend.repository;

import com.ssbackend.ssbackend.entity.District;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DistrictRepository extends JpaRepository<District, Long> {
    Page<District> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<District> findByState_Id(Long stateId, Pageable pageable);
}
