package com.ssbackend.ssbackend.service.impl;

import com.ssbackend.ssbackend.controller.ResourceNotFoundException;
import com.ssbackend.ssbackend.entity.District;
import com.ssbackend.ssbackend.entity.State;
import com.ssbackend.ssbackend.repository.DistrictRepository;
import com.ssbackend.ssbackend.repository.StateRepository;
import com.ssbackend.ssbackend.service.DistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DistrictServiceImpl implements DistrictService {
    private final DistrictRepository districtRepo;
    private final StateRepository stateRepo;

    @Override
    public District create(District d, Long stateId) {
        State state = stateRepo.findById(stateId)
                .orElseThrow(() -> new ResourceNotFoundException("State not found: " + stateId));
        d.setState(state);
        return districtRepo.save(d);
    }

    @Override
    public District update(Long id, District d, Long stateId) {
        District existing = districtRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("District not found: " + id));
        State state = stateRepo.findById(stateId)
                .orElseThrow(() -> new ResourceNotFoundException("State not found: " + stateId));
        existing.setName(d.getName());
        existing.setState(state);
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
    public Page<District> list(Long stateId, String q, Pageable pageable) {
        if (stateId != null) return districtRepo.findByState_Id(stateId, pageable);
        if (q != null && !q.isBlank()) return districtRepo.findByNameContainingIgnoreCase(q, pageable);
        return districtRepo.findAll(pageable);
    }
}
