package com.tmobile.shared.dto;

// Clean, immutable Java 21 Record sharing cross-service data models
public record ProductDetails(
        String productId,
        boolean isAvailable,
        double currentPrice,
        String warehouseLocation
) {}
