package com.example.CarBackend.Repository;

import com.example.CarBackend.Model.CarModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends MongoRepository<CarModel, String> {
}
