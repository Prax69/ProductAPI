package com.productapp.restapiproduct.service;

import com.productapp.restapiproduct.entity.Product;
import com.productapp.restapiproduct.entity.ProductDTO;

import java.util.List;

public interface ProductService {

    List<ProductDTO> getAllProducts();

    Product findById(int id);

    Product save(ProductDTO productDTO);

    void deleteById(int id);

}
