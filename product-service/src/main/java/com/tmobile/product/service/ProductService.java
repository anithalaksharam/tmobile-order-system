package com.tmobile.product.service;

import com.tmobile.product.model.Product;
import com.tmobile.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    // Constructor injection guarantees the repository dependency is immutable and easy to unit test
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getProductsByPlan(String planType) {
        return productRepository.findByPlanType(planType);
    }

    public Product saveProduct(Product product) {
        // You can add business logic filters here before hitting the database
        return productRepository.save(product);
    }
}
