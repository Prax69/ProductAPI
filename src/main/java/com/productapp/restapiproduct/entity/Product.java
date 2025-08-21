package com.productapp.restapiproduct.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class Product {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    int id;
    String name;
    String description;
    int price;
    int quantity;

    private String category;
    private String supplier;
}
