package com.example.demo.Model;

// Shape of one "joined" row: an order plus the name of the user who placed it.
public record OrderDetails(String orderId, int total, String userName) {
}
