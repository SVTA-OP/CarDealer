package com.example.demo.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "CarDealership")
public class CarModel {

    @Id
    private String id;

    private String brand;
    private String model;
    private int year;
    private String type;
    private String fuelType;
    private double pricePerDay;
    private boolean available;
    private List<String> features;

}
