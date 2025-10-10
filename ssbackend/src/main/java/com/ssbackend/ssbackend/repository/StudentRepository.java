package com.ssbackend.ssbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssbackend.ssbackend.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
