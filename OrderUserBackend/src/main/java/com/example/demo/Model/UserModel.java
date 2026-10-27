package com.example.demo.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
public class UserModel {
    @Id
    private String id;

    private int userId;     // business key: orders point to this
    private String name;
}
