package com.example.demo.Controller;

import com.example.demo.Model.OrderDetails;
import com.example.demo.Model.OrderModel;
import com.example.demo.Model.UserModel;
import com.example.demo.Service.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class Controller {

    private final Service service;

    public Controller(Service service) {
        this.service = service;
    }

    @GetMapping("/users")
    public List<UserModel> users() { return service.listUsers(); }

    @PostMapping("/users")
    public UserModel addUser(@RequestBody UserModel user) { return service.addUser(user); }

    @GetMapping("/orders")
    public List<OrderModel> orders() { return service.listOrders(); }

    @PostMapping("/orders")
    public OrderModel addOrder(@RequestBody OrderModel order) { return service.addOrder(order); }

    // joined data: every order with its user's name
    @GetMapping("/orders/details")
    public List<OrderDetails> details() { return service.orderDetails(null); }

    // joined data for one order, e.g. /orders/details/O-101
    @GetMapping("/orders/details/{orderId}")
    public List<OrderDetails> detailsOf(@PathVariable String orderId) { return service.orderDetails(orderId); }
}
