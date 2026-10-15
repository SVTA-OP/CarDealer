package com.example.demo.Controller;

import com.example.demo.Model.CarModel;
import com.example.demo.Service.CarService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
    @CrossOrigin( origins="*")

public class CarController {

    private final CarService service;

    public CarController(CarService service){
        this.service = service;
    }

    @GetMapping
    public List<CarModel> getAllCars(){
        return service.getAll();
    }

    @PostMapping("/create")
    public CarModel createCar(@RequestBody CarModel carModel){
       return service.addCar(carModel);
    }

    @PutMapping("/update/{id}")
    public CarModel updateModel(@PathVariable String id, @RequestParam String newname){
        return service.updateCarNamee(id, newname);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCar(@PathVariable String id){
        service.deleteCar(id);
        return "Deleted " + id;
    }
}
