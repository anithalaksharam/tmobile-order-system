package com.tmobile.product.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Document(collection = "products")
public class Product {

    @Id
    private String id; // MongoDB handles auto-generation for String IDs

    @NotBlank(message = "Product name cannot be empty")
    private String name;

    @Min(value = 0, message = "Price must be greater than or equal to 0")
    private Double price;

    @NotBlank(message = "Plan type cannot be empty")
    private String planType; // e.g., "PREPAID", "POSTPAID"

    // No-argument Constructor (Required by MongoDB reflection)
    public Product() {}

    // Parameterized Constructor
    public Product(String name, Double price, String planType) {
        this.name = name;
        this.price = price;
        this.planType = planType;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getPlanType() { return planType; }
    public void setPlanType(String planType) { this.planType = planType; }
}
