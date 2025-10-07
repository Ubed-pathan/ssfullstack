package com.ssbackend.ssbackend.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ssbackend.ssbackend.controller.dto.Dtos.CountryCreateReq;
import com.ssbackend.ssbackend.controller.dto.Dtos.CountryUpdateReq;
import com.ssbackend.ssbackend.entity.Country;
import com.ssbackend.ssbackend.service.CloudinaryUploadService;
import com.ssbackend.ssbackend.service.CountryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/countries")
@RequiredArgsConstructor
public class CountryController {
    private final CountryService service;
    private final CloudinaryUploadService uploadService;

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

    // Upload or replace country image and persist URL
    @PostMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Country uploadImage(@PathVariable Long id,
                               @RequestParam("file") MultipartFile file) throws Exception {
        String url = uploadService.uploadImage(file, "ssfullstack/country images");
        return service.setImageUrl(id, url);
    }
}
