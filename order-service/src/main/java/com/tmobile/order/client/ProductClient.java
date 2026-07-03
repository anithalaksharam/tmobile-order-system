package com.tmobile.order.client;

import com.tmobile.shared.dto.ProductDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ProductClient {

    private final RestClient restClient;

    // Injecting RestClient.Builder via Constructor (Industry Best Practice)
    public ProductClient(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("http://localhost:8011/api/v1/products")
                .build();
    }

    // Call 1: Keeps your original lightweight availability check working
    public boolean checkProductAvailability(String productId) {
        try {
            System.out.println("Modern RestClient checking availability for: " + productId);
            return restClient.get()
                    .uri("/{id}/availability", productId)
                    .retrieve()
                    .body(Boolean.class);
        } catch (Exception e) {
            System.err.println("RestClient availability check failed: " + e.getMessage());
            return false;
        }
    }

    // Call 2: Hits your rich product details endpoint and returns the shared record DTO!
    public ProductDetails checkProductDetails(String productId) {
        try {
            System.out.println("Modern RestClient fetching details for: " + productId);
            return restClient.get()
                    .uri("/{id}/details", productId)
                    .retrieve()
                    .body(ProductDetails.class); // Automatically maps JSON to your Shared Record!
        } catch (Exception e) {
            System.err.println("RestClient details fetch failed: " + e.getMessage());
            return new ProductDetails(productId, false, 0.0, "UNKNOWN");
        }
    }
}
