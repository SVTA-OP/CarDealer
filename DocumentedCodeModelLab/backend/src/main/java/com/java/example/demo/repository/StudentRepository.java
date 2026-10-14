package com.java.example.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.java.example.demo.model.Student;

/**
 * REPOSITORY = the layer that talks to the database.
 *
 * By extending MongoRepository<Student, String> we get these for FREE:
 *   save(), findAll(), findById(), deleteById(), existsById() ...
 * (Student = the model type, String = type of the id field)
 * No implementation code is needed - Spring creates it at runtime.
 */
@Repository
public interface StudentRepository extends MongoRepository<Student, String> {
    // Nothing to write here for basic CRUD!
}
