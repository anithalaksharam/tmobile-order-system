package com.tmobile.product.controller;

import com.tmobile.shared.dto.ProductDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
public class ProductAvailabilityController {

    // Function 1: The original, fast, lightweight check
    @GetMapping("/{id}/availability")
    public ResponseEntity<Boolean> checkAvailability(@PathVariable("id") String productId) {
        System.out.println("Processing simple availability check for: " + productId);
        boolean isAvailable = !"out-of-stock-id".equals(productId);
        return ResponseEntity.ok(isAvailable);
    }

    // Function 2: The new, rich data check (Renamed as requested)
    @GetMapping("/{id}/details")
    public ResponseEntity<ProductDetails> checkProductDetails(@PathVariable("id") String productId) {
        System.out.println("Processing comprehensive details fetch for: " + productId);

        boolean stockCheck = !"out-of-stock-id".equals(productId);
        double mockPrice = "iphone15".equals(productId) ? 999.99 : 799.99;

        ProductDetails details = new ProductDetails(productId, stockCheck, mockPrice, "Seattle-Warehouse-A");
        return ResponseEntity.ok(details);
    }
}
