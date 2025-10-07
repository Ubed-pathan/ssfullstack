package com.ssbackend.ssbackend.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ssbackend.ssbackend.entity.District;

public interface DistrictService {
    District create(District d);
    District update(Long id, District d);
    void delete(Long id);
    Optional<District> get(Long id);
    Page<District> list(String state, String q, Pageable pageable);
}
