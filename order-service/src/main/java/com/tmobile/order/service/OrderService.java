package com.tmobile.order.service;

import com.tmobile.order.client.ProductClient;
import com.tmobile.order.model.Order;
import com.tmobile.order.repository.OrderRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class OrderService {

    private final ProductClient productClient;
    private final OrderRepository orderRepository;

    public OrderService(ProductClient productClient, OrderRepository orderRepository) {
        this.productClient = productClient;
        this.orderRepository = orderRepository;
    }

    public Mono<String> processOrderAsync(String productId, int quantity) {
        // STEP 1: Run the lightweight availability check first
        return productClient.checkProductAvailabilityAsync(productId)
                .flatMap(isAvailable -> {

                    // Fail-Fast: If it's not available, stop the pipeline right here
                    if (!isAvailable) {
                        return Mono.just("Order Rejected: Initial availability check failed.");
                    }

                    // STEP 2: If available, fetch rich details to get pricing and warehouse location
                    return productClient.checkProductDetailsAsync(productId)
                            .flatMap(productDetails -> {

                                // Double check availability flag inside the rich payload
                                if (!productDetails.isAvailable()) {
                                    return Mono.just("Order Rejected: Product became unavailable during processing.");
                                }

                                // STEP 3: Execute corporate business rules and math calculations
                                double totalPrice = productDetails.currentPrice() * quantity;

                                Order order = new Order();
                                order.setProductId(productId);
                                order.setQuantity(quantity);
                                order.setTotalOrderPrice(totalPrice);
                                order.setStatus("PROCESSING");
                                order.setWarehouseLocation(productDetails.warehouseLocation());

                                // STEP 4: Persist the entity into MongoDB
                                orderRepository.save(order);

                                return Mono.just("Order accepted from " + productDetails.warehouseLocation() + ". Total charges: $" + totalPrice);
                            });
                });
    }
}
