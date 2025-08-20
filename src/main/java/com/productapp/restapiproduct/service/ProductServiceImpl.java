package com.productapp.restapiproduct.service;


import com.productapp.restapiproduct.entity.Product;
import com.productapp.restapiproduct.entity.ProductDTO;
import com.productapp.restapiproduct.mapper.UniversalMapper;
import com.productapp.restapiproduct.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UniversalMapper mapper;

    public ProductServiceImpl(ProductRepository productRepository, UniversalMapper mapper) {
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return mapper.mapList(productRepository.findAll(), ProductDTO.class);
    }

    @Override
    public Product findById(int id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    @Override
    public Product save(ProductDTO productDTO) {
        Product product = mapper.map(productDTO, Product.class);
        return productRepository.save(product);
    }

    @Override
    public void deleteById(int id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }
}
