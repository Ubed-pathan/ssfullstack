package com.ssbackend.ssbackend.service;

import com.ssbackend.ssbackend.entity.District;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface DistrictService {
    District create(District d, Long stateId);
    District update(Long id, District d, Long stateId);
    void delete(Long id);
    Optional<District> get(Long id);
    Page<District> list(Long stateId, String q, Pageable pageable);
}
