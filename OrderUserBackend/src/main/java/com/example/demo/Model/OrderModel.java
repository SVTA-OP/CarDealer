package com.example.demo.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "orders")
public class OrderModel {
    @Id
    private String id;

    private String orderId;
    private int userId;     // matches UserModel.userId
    private int total;
}
