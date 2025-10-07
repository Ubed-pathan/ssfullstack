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

import com.ssbackend.ssbackend.controller.dto.Dtos.StateCreateReq;
import com.ssbackend.ssbackend.controller.dto.Dtos.StateUpdateReq;
import com.ssbackend.ssbackend.entity.State;
import com.ssbackend.ssbackend.service.StateService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/states")
@RequiredArgsConstructor
public class StateController {
    private final StateService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public State create(@Valid @RequestBody StateCreateReq req) {
        State s = State.builder().name(req.name()).country(req.country()).build();
        return service.create(s);
    }

    @PutMapping("/{id}")
    public State update(@PathVariable Long id, @Valid @RequestBody StateUpdateReq req) {
        State s = State.builder().name(req.name()).country(req.country()).build();
        return service.update(id, s);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.delete(id); }

    @GetMapping("/{id}")
    public State get(@PathVariable Long id) {
        return service.get(id).orElseThrow(() -> new ResourceNotFoundException("State not found: " + id));
    }

    @GetMapping
    public Page<State> list(@RequestParam(required = false) String country,
                            @RequestParam(required = false) String q,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return service.list(country, q, pageable);
    }
}
