package com.ssbackend.ssbackend.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ssbackend.ssbackend.controller.dto.Dtos.LanguageCreateReq;
import com.ssbackend.ssbackend.controller.dto.Dtos.LanguageUpdateReq;
import com.ssbackend.ssbackend.entity.Language;
import com.ssbackend.ssbackend.service.LanguageService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/languages")
@RequiredArgsConstructor
public class LanguageController {
    private final LanguageService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Language create(@Valid @RequestBody LanguageCreateReq req) {
        Language l = Language.builder().name(req.name()).build();
        return service.create(l);
    }

    @PutMapping("/{id}")
    public Language update(@PathVariable Long id, @Valid @RequestBody LanguageUpdateReq req) {
        Language l = Language.builder().name(req.name()).build();
        return service.update(id, l);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.delete(id); }

    @GetMapping("/{id}")
    public Language get(@PathVariable Long id) {
        return service.get(id).orElseThrow(() -> new ResourceNotFoundException("Language not found: " + id));
    }

    @GetMapping
    public Page<Language> list(@RequestParam(required = false) String q,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return service.list(q, pageable);
    }
}
