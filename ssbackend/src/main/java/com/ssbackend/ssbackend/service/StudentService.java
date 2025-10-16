package com.ssbackend.ssbackend.service;

import java.util.List;
import java.util.Optional;

import com.ssbackend.ssbackend.entity.Student;

public interface StudentService {
    Student create(Student s);
    List<Student> findAll();
    Optional<Student> get(Long id);
    Student update(Long id, Student s);
    void delete(Long id);
}


