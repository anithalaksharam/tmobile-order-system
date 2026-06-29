package com.tmobile.shared.dto;

public class ProductDTO {
    private String id;
    private String name;
    private Double price;
    private String planType;

    public ProductDTO() {}

    public ProductDTO(String id, String name, Double price, String planType) {
        this.id = id;
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
