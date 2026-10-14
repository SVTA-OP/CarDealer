package com.java.example.demo.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * MODEL = one student document stored in MongoDB.
 *
 * Lombok annotations (no need to write boilerplate by hand):
 *   @Data               -> creates getters, setters, toString, equals, hashCode
 *   @NoArgsConstructor  -> creates an empty constructor (needed for JSON conversion)
 *   @AllArgsConstructor -> creates a constructor with all fields
 *
 * @Document tells MongoDB which collection (like a table) to use.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "students")
public class Student {

    @Id                       // MongoDB generates this unique id automatically
    private String id;

    private String name;
    private String email;
    private String course;
    private int age;
    private String photoUrl;      // image is just a URL
    private List<String> skills;  // e.g. ["Java", "Vue"]
    private String joinedDate;    // format: yyyy-MM-dd
}
