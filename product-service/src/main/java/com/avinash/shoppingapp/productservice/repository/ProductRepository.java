package com.avinash.shoppingapp.productservice.repository;


import com.avinash.shoppingapp.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
