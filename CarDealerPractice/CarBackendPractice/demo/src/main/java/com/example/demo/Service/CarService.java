package com.example.demo.Service;

import com.example.demo.Model.CarModel;
import com.example.demo.Repository.CarRepository;
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

    public String deleteCar(String id){
        repo.deleteById(id);
        return "Deleted" + id;
    }

    public CarModel addCar(CarModel model){
        return repo.save(model);
    }

    public CarModel updateCarNamee(String id, String newname){
        return repo.findById(id).map(CarModel -> {
            CarModel.setModel(newname);
            return repo.save(CarModel);
        }).orElse(null);
    }

}
