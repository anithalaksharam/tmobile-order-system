package com.tmobile.order.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Document(collection = "orders")
public class Order {

    @Id
    private String id;

    @NotBlank(message = "Customer ID cannot be empty")
    private String customerId;

    @NotBlank(message = "Product ID cannot be empty")
    private String productId;

    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    private Double totalOrderPrice;
    private String status; // e.g., "PENDING", "COMPLETED"
    private LocalDateTime orderDate;

    // No-argument Constructor (Required by MongoDB reflection)
    public Order() {}

    // Parameterized Constructor
    public Order(String customerId, String productId, Integer quantity) {
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public Double getTotalOrderPrice() { return totalOrderPrice; }
    public void setTotalOrderPrice(Double totalOrderPrice) { this.totalOrderPrice = totalOrderPrice; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }
}
