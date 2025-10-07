package com.ssbackend.ssbackend.service;

import com.ssbackend.ssbackend.entity.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface StateService {
    State create(State s, Long countryId);
    State update(Long id, State s, Long countryId);
    void delete(Long id);
    Optional<State> get(Long id);
    Page<State> list(Long countryId, String q, Pageable pageable);
}
