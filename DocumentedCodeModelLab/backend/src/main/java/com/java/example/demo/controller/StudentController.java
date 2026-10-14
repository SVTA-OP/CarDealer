package com.java.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.example.demo.model.Student;
import com.java.example.demo.service.StudentService;

import lombok.RequiredArgsConstructor;

/**
 * CONTROLLER = the layer that receives HTTP requests from the Vue frontend.
 *
 *   HTTP method | URL                   | What it does
 *   ------------+-----------------------+--------------------
 *   GET         | /api/students         | get all students
 *   GET         | /api/students/{id}    | get one student
 *   POST        | /api/students         | create a student
 *   PUT         | /api/students/{id}    | update a student
 *   DELETE      | /api/students/{id}    | delete a student
 *
 * @CrossOrigin lets the Vue app (running on port 5173) call this server (port 8080).
 * Without it, the browser would block the requests (CORS rule).
 */
@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService service;

    // ---------- READ ALL ----------
    @GetMapping
    public List<Student> getAll() {
        return service.getAll();
    }

    // ---------- READ ONE ----------
    // @PathVariable takes {id} from the URL
    @GetMapping("/{id}")
    public ResponseEntity<Student> getById(@PathVariable String id) {
        return service.getById(id)
                .map(ResponseEntity::ok)                      // found   -> 200 OK
                .orElse(ResponseEntity.notFound().build());   // missing -> 404
    }

    // ---------- CREATE ----------
    // @RequestBody converts the JSON sent by Vue into a Student object
    @PostMapping
    public Student create(@RequestBody Student student) {
        return service.create(student);
    }

    // ---------- UPDATE ----------
    @PutMapping("/{id}")
    public ResponseEntity<Student> update(@PathVariable String id, @RequestBody Student student) {
        return service.update(id, student)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ---------- DELETE ----------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();   // 204 = deleted, nothing to return
        }
        return ResponseEntity.notFound().build();        // 404
    }
}
