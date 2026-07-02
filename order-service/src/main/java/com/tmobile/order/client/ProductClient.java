package com.tmobile.order.client;

import com.tmobile.shared.dto.ProductDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ProductClient {

    private final RestTemplate restTemplate;

    public ProductClient() {
        this.restTemplate = new RestTemplate();
    }

    // Client Call 1: Targets the Boolean path
    public boolean checkProductAvailability(String productId) {
        String url = "http://localhost:8011/api/v1/products/" + productId + "/availability";
        try {
            return restTemplate.getForObject(url, Boolean.class);
        } catch (Exception e) {
            return false;
        }
    }

    // Client Call 2: Targets the rich ProductDetails path (Renamed as requested)
    public ProductDetails checkProductDetails(String productId) {
        String url = "http://localhost:8011/api/v1/products/" + productId + "/details";
        try {
            return restTemplate.getForObject(url, ProductDetails.class);
        } catch (Exception e) {
            return new ProductDetails(productId, false, 0.0, "UNKNOWN");
        }
    }
}
