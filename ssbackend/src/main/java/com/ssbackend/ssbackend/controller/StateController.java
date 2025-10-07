package com.ssbackend.ssbackend.controller;

import com.ssbackend.ssbackend.controller.dto.Dtos.StateCreateReq;
import com.ssbackend.ssbackend.controller.dto.Dtos.StateUpdateReq;
import com.ssbackend.ssbackend.entity.State;
import com.ssbackend.ssbackend.service.StateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/states")
@RequiredArgsConstructor
public class StateController {
    private final StateService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public State create(@Valid @RequestBody StateCreateReq req) {
        State s = State.builder().name(req.name()).code(req.code()).build();
        return service.create(s, req.countryId());
    }

    @PutMapping("/{id}")
    public State update(@PathVariable Long id, @Valid @RequestBody StateUpdateReq req) {
        State s = State.builder().name(req.name()).code(req.code()).build();
        return service.update(id, s, req.countryId());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.delete(id); }

    @GetMapping("/{id}")
    public State get(@PathVariable Long id) {
        return service.get(id).orElseThrow(() -> new ResourceNotFoundException("State not found: " + id));
    }

    @GetMapping
    public Page<State> list(@RequestParam(required = false) Long countryId,
                            @RequestParam(required = false) String q,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return service.list(countryId, q, pageable);
    }
}
