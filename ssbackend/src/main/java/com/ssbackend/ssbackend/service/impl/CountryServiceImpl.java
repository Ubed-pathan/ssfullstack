package com.ssbackend.ssbackend.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssbackend.ssbackend.controller.ResourceNotFoundException;
import com.ssbackend.ssbackend.entity.Country;
import com.ssbackend.ssbackend.repository.CountryRepository;
import com.ssbackend.ssbackend.service.CountryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CountryServiceImpl implements CountryService {
    private final CountryRepository repo;

    @Override
    public Country create(Country c) {
        return repo.save(c);
    }

    @Override
    public Country update(Long id, Country c) {
        Country existing = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Country not found: " + id));
        existing.setName(c.getName());
        return repo.save(existing);
    }

    @Override
    public Country setImageUrl(Long id, String imageUrl) {
        Country existing = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Country not found: " + id));
        existing.setImageUrl(imageUrl);
        return repo.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new ResourceNotFoundException("Country not found: " + id);
        repo.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Country> get(Long id) { return repo.findById(id); }

    @Override
    @Transactional(readOnly = true)
    public Page<Country> list(String q, Pageable pageable) {
        if (q != null && !q.isBlank()) return repo.findByNameContainingIgnoreCase(q, pageable);
        return repo.findAll(pageable);
    }
}
