package com.example.demo.Service;

import com.example.demo.Model.OrderDetails;
import com.example.demo.Model.OrderModel;
import com.example.demo.Model.UserModel;
import com.example.demo.Repository.OrderRepository;
import com.example.demo.Repository.UserRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class Service {

    private final OrderRepository orepo;
    private final UserRepository urepo;
    private final MongoTemplate mongo;

    public Service(OrderRepository orepo, UserRepository urepo, MongoTemplate mongo) {
        this.orepo = orepo;
        this.urepo = urepo;
        this.mongo = mongo;
    }

    public List<UserModel> listUsers()         { return urepo.findAll(); }
    public UserModel addUser(UserModel u)      { return urepo.save(u); }
    public List<OrderModel> listOrders()       { return orepo.findAll(); }
    public OrderModel addOrder(OrderModel o)   { return orepo.save(o); }

    // The "join": orders JOIN users ON orders.userId = users.userId.
    // Pass an orderId to get one order, or null to get all of them.
    public List<OrderDetails> orderDetails(String orderId) {
        List<AggregationOperation> steps = new ArrayList<>();

        if (orderId != null) {
            steps.add(Aggregation.match(Criteria.where("orderId").is(orderId)));
        }
        steps.add(Aggregation.lookup("users", "userId", "userId", "user")); // attach matching users as array "user"
        steps.add(Aggregation.unwind("user"));                               // flatten to one user per order
        steps.add(Aggregation.project("orderId", "total").and("user.name").as("userName"));

        return mongo.aggregate(Aggregation.newAggregation(steps), "orders", OrderModel.class) // Use the model class for this class and add the userName variable in the model
                    .getMappedResults();
    }
}
