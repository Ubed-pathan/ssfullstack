package com.ssbackend.ssbackend.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssbackend.ssbackend.controller.ResourceNotFoundException;
import com.ssbackend.ssbackend.entity.District;
import com.ssbackend.ssbackend.repository.DistrictRepository;
import com.ssbackend.ssbackend.service.DistrictService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class DistrictServiceImpl implements DistrictService {
    private final DistrictRepository districtRepo;

    @Override
    public District create(District d) { return districtRepo.save(d); }

    @Override
    public District update(Long id, District d) {
        District existing = districtRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("District not found: " + id));
        existing.setName(d.getName());
        existing.setState(d.getState());
        return districtRepo.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (!districtRepo.existsById(id)) throw new ResourceNotFoundException("District not found: " + id);
        districtRepo.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<District> get(Long id) { return districtRepo.findById(id); }

    @Override
    @Transactional(readOnly = true)
    public Page<District> list(String state, String q, Pageable pageable) {
        if (state != null && !state.isBlank()) return districtRepo.findByStateContainingIgnoreCase(state, pageable);
        if (q != null && !q.isBlank()) return districtRepo.findByNameContainingIgnoreCase(q, pageable);
        return districtRepo.findAll(pageable);
    }
}
