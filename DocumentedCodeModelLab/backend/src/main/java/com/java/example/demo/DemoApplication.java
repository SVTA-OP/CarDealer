package com.java.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ENTRY POINT of the Spring Boot app.
 * Run this class (or `mvn spring-boot:run`) to start the server.
 * @SpringBootApplication scans this package and all sub-packages
 * (controller, service, repository, model) and wires everything together.
 */
@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
