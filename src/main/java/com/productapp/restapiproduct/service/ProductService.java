package com.productapp.restapiproduct.service;

import com.productapp.restapiproduct.entity.FilterDTO;
import com.productapp.restapiproduct.entity.Product;
import com.productapp.restapiproduct.entity.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    List<ProductDTO> getAllProducts();

    Product findById(int id);

    Product save(ProductDTO productDTO);

    void deleteById(int id);

    List<ProductDTO> filterByPriceRange(double minPrice, double maxPrice);

    List<ProductDTO> sortByPriceAsc();

    List<ProductDTO> sortByPriceDesc();

    List<ProductDTO> filterByQuantity(double minQuantity);

    // List<ProductDTO> filterProducts(String category, String supplier, double minPrice, double maxPrice, Boolean inStock);

    List<ProductDTO> sortByQuantityAsc();

    List<ProductDTO> sortByQuantityDesc();

    List<ProductDTO> filterProducts(FilterDTO filterDTO);

    Page<ProductDTO> getSortedProducts(String sortBy, Pageable pageable);


}
