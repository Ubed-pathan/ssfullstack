package com.ssbackend.ssbackend.repository;

import com.ssbackend.ssbackend.entity.Language;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Language, Long> {
    boolean existsByNameIgnoreCase(String name);
    Page<Language> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
