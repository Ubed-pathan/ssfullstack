package com.ssbackend.ssbackend.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssbackend.ssbackend.controller.ResourceNotFoundException;
import com.ssbackend.ssbackend.entity.State;
import com.ssbackend.ssbackend.repository.StateRepository;
import com.ssbackend.ssbackend.service.StateService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class StateServiceImpl implements StateService {
    private final StateRepository stateRepo;

    @Override
    public State create(State s) { return stateRepo.save(s); }

    @Override
    public State update(Long id, State s) {
        State existing = stateRepo.findById(id)
                .orElseThrow(() -> new com.ssbackend.ssbackend.controller.ResourceNotFoundException("State not found: " + id));
        existing.setName(s.getName());
        existing.setCountry(s.getCountry());
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
    public Page<State> list(String country, String q, Pageable pageable) {
        if (country != null && !country.isBlank()) return stateRepo.findByCountryContainingIgnoreCase(country, pageable);
        if (q != null && !q.isBlank()) return stateRepo.findByNameContainingIgnoreCase(q, pageable);
        return stateRepo.findAll(pageable);
    }
}
