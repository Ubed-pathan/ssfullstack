package com.ssbackend.ssbackend.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssbackend.ssbackend.entity.Student;
import com.ssbackend.ssbackend.repository.StudentRepository;
import com.ssbackend.ssbackend.service.StudentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentServiceImpl implements StudentService {
    private final StudentRepository repo;

    @Override
    public Student create(Student s) { return repo.save(s); }

    @Override @Transactional(readOnly = true)
    public List<Student> findAll() { return repo.findAll(); }

    @Override @Transactional(readOnly = true)
    public Optional<Student> get(Long id) { return repo.findById(id); }

    @Override
    public Student update(Long id, Student s) {
        Student existing = repo.findById(id).orElseThrow(() -> new com.ssbackend.ssbackend.controller.ResourceNotFoundException("Student not found: " + id));
        existing.setName(s.getName());
        existing.setEmail(s.getEmail());
        existing.setMobile(s.getMobile());
        existing.setCountry(s.getCountry());
        existing.setState(s.getState());
        existing.setDistrict(s.getDistrict());
        existing.setGender(s.getGender());
        return repo.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new com.ssbackend.ssbackend.controller.ResourceNotFoundException("Student not found: " + id);
        }
        repo.deleteById(id);
    }
}
