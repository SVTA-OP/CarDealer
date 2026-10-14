package com.java.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.java.example.demo.model.Student;
import com.java.example.demo.repository.StudentRepository;

import lombok.RequiredArgsConstructor;

/**
 * SERVICE = the layer for business logic.
 * The controller calls the service, and the service calls the repository.
 *
 * @RequiredArgsConstructor (Lombok) writes a constructor for the "final" field,
 * and Spring uses it to inject the repository automatically.
 */
@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository repository;

    // READ all students
    public List<Student> getAll() {
        return repository.findAll();
    }

    // READ one student by id (Optional = may be empty if not found)
    public Optional<Student> getById(String id) {
        return repository.findById(id);
    }

    // CREATE a new student (id is null, so MongoDB creates a new document)
    public Student create(Student student) {
        return repository.save(student);
    }

    // UPDATE an existing student
    public Optional<Student> update(String id, Student newData) {
        // If a student with this id exists, replace its data; otherwise return empty
        return repository.findById(id).map(existing -> {
            existing.setName(newData.getName());
            existing.setEmail(newData.getEmail());
            existing.setCourse(newData.getCourse());
            existing.setAge(newData.getAge());
            existing.setPhotoUrl(newData.getPhotoUrl());
            existing.setSkills(newData.getSkills());
            existing.setJoinedDate(newData.getJoinedDate());
            return repository.save(existing); // save() on an existing id = update
        });
    }

    // DELETE a student. Returns true if it existed, false if not.
    public boolean delete(String id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
