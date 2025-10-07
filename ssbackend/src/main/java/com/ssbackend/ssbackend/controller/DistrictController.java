package com.ssbackend.ssbackend.controller;

import com.ssbackend.ssbackend.controller.dto.Dtos.DistrictCreateReq;
import com.ssbackend.ssbackend.controller.dto.Dtos.DistrictUpdateReq;
import com.ssbackend.ssbackend.entity.District;
import com.ssbackend.ssbackend.service.DistrictService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/districts")
@RequiredArgsConstructor
public class DistrictController {
    private final DistrictService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public District create(@Valid @RequestBody DistrictCreateReq req) {
        District d = District.builder().name(req.name()).state(req.state()).build();
        return service.create(d);
    }

    @PutMapping("/{id}")
    public District update(@PathVariable Long id, @Valid @RequestBody DistrictUpdateReq req) {
        District d = District.builder().name(req.name()).state(req.state()).build();
        return service.update(id, d);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.delete(id); }

    @GetMapping("/{id}")
    public District get(@PathVariable Long id) {
        return service.get(id).orElseThrow(() -> new ResourceNotFoundException("District not found: " + id));
    }

    @GetMapping
    public Page<District> list(@RequestParam(required = false) String state,
                               @RequestParam(required = false) String q,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return service.list(state, q, pageable);
    }
}
