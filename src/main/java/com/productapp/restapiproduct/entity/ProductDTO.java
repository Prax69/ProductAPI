package com.productapp.restapiproduct.entity;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductDTO {

    private int id;
    @NotEmpty(message = "Name cannot be empty")
    private String name;
    private String description;
    @NotNull(message = "Price cannot be empty")
    private Integer price;
    @NotNull(message = "Quantity cannot be empty")
    private Integer quantity;

    @NotEmpty(message = "Category cannot be empty")
    private String category;
    @NotEmpty(message = "Supplier cannot be empty")
    private String supplier;
}
