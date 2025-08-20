package com.productapp.restapiproduct.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name= "users")
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;
    private String username;
    private String password;
}
