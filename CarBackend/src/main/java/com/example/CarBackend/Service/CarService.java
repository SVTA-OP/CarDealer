package com.example.CarBackend.Service;

import com.example.CarBackend.Model.CarModel;
import com.example.CarBackend.Repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    private final CarRepository repo;

    public CarService(CarRepository repo){
        this.repo = repo;
    }

    public List<CarModel> getAll(){
        return repo.findAll();
    }

    public CarModel getById(String id){
        return repo.findById(id).orElse(null);
    }

    public String deleteById(String id){
        repo.deleteById(id);
        return "Deleted";
    }
    public CarModel changeNameById(String id, String newname){
        return repo.findById(id).map(car -> {
            car.setModel(newname);
           return repo.save(car);
        }).orElse(null);

    }

    public CarModel addCar(CarModel model){
        return repo.save(model);
    }

}
