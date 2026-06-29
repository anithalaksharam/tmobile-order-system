package com.tmobile.product.repository;

import com.tmobile.product.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {
    // Dynamic Query Method: Spring Boot auto-generates the database query based on this name!
    List<Product> findByPlanType(String planType);
}
