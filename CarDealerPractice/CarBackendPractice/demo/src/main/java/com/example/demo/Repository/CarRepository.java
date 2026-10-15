package com.example.demo.Repository;

import com.example.demo.Model.CarModel;
import org.springframework.data.mongodb.repository.MongoRepository;



public interface CarRepository extends MongoRepository<CarModel, String> {
}
