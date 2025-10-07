package com.ssbackend.ssbackend.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ssbackend.ssbackend.entity.State;

public interface StateService {
    State create(State s);
    State update(Long id, State s);
    void delete(Long id);
    Optional<State> get(Long id);
    Page<State> list(String country, String q, Pageable pageable);
}
