package com.ssbackend.ssbackend.service;

import com.ssbackend.ssbackend.entity.Language;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface LanguageService {
    Language create(Language l);
    Language update(Long id, Language l);
    void delete(Long id);
    Optional<Language> get(Long id);
    Page<Language> list(String q, Pageable pageable);
}
