package com.ssbackend.ssbackend.service.impl;

import com.ssbackend.ssbackend.controller.ResourceNotFoundException;
import com.ssbackend.ssbackend.entity.Country;
import com.ssbackend.ssbackend.entity.State;
import com.ssbackend.ssbackend.repository.CountryRepository;
import com.ssbackend.ssbackend.repository.StateRepository;
import com.ssbackend.ssbackend.service.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class StateServiceImpl implements StateService {
    private final StateRepository stateRepo;
    private final CountryRepository countryRepo;

    @Override
    public State create(State s, Long countryId) {
        Country country = countryRepo.findById(countryId)
                .orElseThrow(() -> new ResourceNotFoundException("Country not found: " + countryId));
        s.setCountry(country);
        return stateRepo.save(s);
    }

    @Override
    public State update(Long id, State s, Long countryId) {
        State existing = stateRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("State not found: " + id));
        Country country = countryRepo.findById(countryId)
                .orElseThrow(() -> new ResourceNotFoundException("Country not found: " + countryId));
        existing.setName(s.getName());
        existing.setCode(s.getCode());
        existing.setCountry(country);
        return stateRepo.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (!stateRepo.existsById(id)) throw new ResourceNotFoundException("State not found: " + id);
        stateRepo.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<State> get(Long id) { return stateRepo.findById(id); }

    @Override
    @Transactional(readOnly = true)
    public Page<State> list(Long countryId, String q, Pageable pageable) {
        if (countryId != null) return stateRepo.findByCountry_Id(countryId, pageable);
        if (q != null && !q.isBlank()) return stateRepo.findByNameContainingIgnoreCase(q, pageable);
        return stateRepo.findAll(pageable);
    }
}
