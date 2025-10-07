package com.ssbackend.ssbackend.service.impl;

import com.ssbackend.ssbackend.controller.ResourceNotFoundException;
import com.ssbackend.ssbackend.entity.Language;
import com.ssbackend.ssbackend.repository.LanguageRepository;
import com.ssbackend.ssbackend.service.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LanguageServiceImpl implements LanguageService {
    private final LanguageRepository repo;

    @Override
    public Language create(Language l) { return repo.save(l); }

    @Override
    public Language update(Long id, Language l) {
        Language existing = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Language not found: " + id));
        existing.setName(l.getName());
        existing.setCode(l.getCode());
        return repo.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new ResourceNotFoundException("Language not found: " + id);
        repo.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Language> get(Long id) { return repo.findById(id); }

    @Override
    @Transactional(readOnly = true)
    public Page<Language> list(String q, Pageable pageable) {
        if (q != null && !q.isBlank()) return repo.findByNameContainingIgnoreCase(q, pageable);
        return repo.findAll(pageable);
    }
}
