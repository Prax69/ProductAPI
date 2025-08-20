package com.productapp.restapiproduct.repository;

import com.productapp.restapiproduct.entity.Product;
import com.productapp.restapiproduct.entity.ProductDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {



}
