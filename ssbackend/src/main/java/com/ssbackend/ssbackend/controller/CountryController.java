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

import com.ssbackend.ssbackend.controller.dto.Dtos.CountryCreateReq;
import com.ssbackend.ssbackend.controller.dto.Dtos.CountryUpdateReq;
import com.ssbackend.ssbackend.entity.Country;
import com.ssbackend.ssbackend.service.CountryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/countries")
@RequiredArgsConstructor
public class CountryController {
    private final CountryService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Country create(@Valid @RequestBody CountryCreateReq req) {
        Country c = Country.builder().name(req.name()).build();
        return service.create(c);
    }

    @PutMapping("/{id}")
    public Country update(@PathVariable Long id, @Valid @RequestBody CountryUpdateReq req) {
        Country c = Country.builder().name(req.name()).build();
        return service.update(id, c);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.delete(id); }

    @GetMapping("/{id}")
    public Country get(@PathVariable Long id) {
        return service.get(id).orElseThrow(() -> new ResourceNotFoundException("Country not found: " + id));
    }

    @GetMapping
    public Page<Country> list(@RequestParam(required = false) String q,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return service.list(q, pageable);
    }
}
