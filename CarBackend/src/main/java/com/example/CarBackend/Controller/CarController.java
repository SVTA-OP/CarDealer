package com.example.CarBackend.Controller;

import com.example.CarBackend.Model.CarModel;
import com.example.CarBackend.Service.CarService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")

public class CarController {
    private final CarService service;

    public CarController(CarService service){
        this.service = service;
    }

    @GetMapping
    public List<CarModel> getAll(){
        return service.getAll();
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable String id){
        return service.deleteById(id);
    }

    @PostMapping("/create")
    public CarModel add(@RequestBody CarModel model){
        return service.addCar(model);
    }

    @PutMapping("/update/{id}")
    public CarModel update(@PathVariable String id, @RequestParam String newname){
        return service.changeNameById(id, newname);
    }

}
