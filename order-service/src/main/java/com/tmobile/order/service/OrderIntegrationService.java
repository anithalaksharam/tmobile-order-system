package com.tmobile.order.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class OrderIntegrationService {

    private final WebClient webClient;

    // Spring Boot automatically injects the WebClient bean from your config package
    public OrderIntegrationService(WebClient webClient) {
        this.webClient = webClient;
    }

    public void processOrderAsync(String orderId) {
        String testApiUrl = "https://typicode.com";

        this.webClient.get()
                .uri(testApiUrl)
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(
                        response -> {
                            // This block executes asynchronously once the API answers
                            System.out.println(">>> Async Order Response for ID " + orderId + ": " + response);
                        },
                        error -> {
                            // Handles network failures without blocking your core application threads
                            System.err.println(">>> Async Error on ID " + orderId + ": " + error.getMessage());
                        }
                );

        System.out.println(">>> Order " + orderId + " dispatched to pipeline! Main thread moving on instantly...");
    }
}
