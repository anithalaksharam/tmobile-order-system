package com.tmobile.order.controller;

import com.tmobile.order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/submit")
    public Mono<ResponseEntity<String>> submitOrder(@RequestParam String productId, @RequestParam int quantity) {
        return orderService.processOrderAsync(productId, quantity)
                .map(message -> {
                    // Check if the pipeline returned a rejection message
                    if (message.contains("Rejected")) {
                        return ResponseEntity.badRequest().body(message);
                    }
                    return ResponseEntity.ok(message);
                });
    }
}
