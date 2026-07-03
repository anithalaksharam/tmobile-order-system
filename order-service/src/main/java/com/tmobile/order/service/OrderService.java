package com.tmobile.order.service;

import com.tmobile.order.client.ProductClient; // <-- Crucial: imports your RestClient wrapper
import com.tmobile.order.model.Order;
import com.tmobile.order.repository.OrderRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    // Single constructor injecting both dependencies automatically via Spring IoC
    public OrderService(OrderRepository orderRepository, ProductClient productClient) {
        this.orderRepository = orderRepository;
        this.productClient = productClient;
    }

    // Keeps your original get method exactly how it was
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Upgraded creation method incorporating the cross-service RestClient check
    public Order createOrder(Order order) {
        System.out.println("OrderService invoking RestClient network hop for product: " + order.getProductId());

        // 1. Fire the outbound network check to product-service (port 8011)
        boolean isAvailable = productClient.checkProductAvailability(order.getProductId());

        // 2. Evaluate response envelope
        if (isAvailable) {
            order.setOrderDate(LocalDateTime.now()); // Sets your live timestamp
            order.setStatus("PENDING");
            return orderRepository.save(order);      // Persists cleanly to MongoDB container
        } else {
            System.err.println("Order validation failed in service layer: " + order.getProductId() + " is out of stock.");
            return null; // Signals controller that the transaction failed validation rules
        }
    }
}
