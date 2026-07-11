package com.tmobile.order.client;

import com.tmobile.shared.dto.ProductDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ProductClient {

    private final WebClient webClient;

    public ProductClient(WebClient webClient) {
        this.webClient = webClient;
    }

    // Call 1: Lightweight availability check (Gatekeeper)
    public Mono<Boolean> checkProductAvailabilityAsync(String productId) {
        return this.webClient.get()
                .uri("/{id}/availability", productId)
                .retrieve()
                .bodyToMono(Boolean.class)
                .onErrorReturn(false); // Default to false if downstream service fails
    }

    // Call 2: Rich data fetch (Executed only if Call 1 passes)
    public Mono<ProductDetails> checkProductDetailsAsync(String productId) {
        return this.webClient.get()
                .uri("/{id}/details", productId)
                .retrieve()
                .bodyToMono(ProductDetails.class)
                .onErrorReturn(new ProductDetails(productId, false, 0.0, "UNKNOWN_WAREHOUSE"));
    }
}
