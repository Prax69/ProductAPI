package com.productapp.restapiproduct.service;


import com.productapp.restapiproduct.entity.FilterDTO;
import com.productapp.restapiproduct.entity.Product;
import com.productapp.restapiproduct.entity.ProductDTO;
import com.productapp.restapiproduct.mapper.UniversalMapper;
import com.productapp.restapiproduct.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    @Override
    public List<ProductDTO> filterByPriceRange(double minPrice, double maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice)
                .stream()
                .map(product -> mapper.map(product, ProductDTO.class))
                .toList();
    }

    @Override
    public List<ProductDTO> sortByPriceAsc() {
        return productRepository.findAllByOrderByPriceAsc()
                .stream()
                .map(product -> mapper.map(product, ProductDTO.class))
                .toList();
    }

    @Override
    public List<ProductDTO> sortByPriceDesc() {
        return productRepository.findAllByOrderByPriceDesc()
                .stream()
                .map(product -> mapper.map(product, ProductDTO.class))
                .toList();
    }

    @Override
    public List<ProductDTO> filterByQuantity(double minQuantity) {
        return productRepository.findByQuantityGreaterThanEqual(minQuantity)
                .stream()
                .map(product -> mapper.map(product, ProductDTO.class))
                .toList();
    }

    @Override
    public List<ProductDTO> filterProducts(String category, String supplier, double minPrice, double maxPrice, Boolean inStock) {
        return productRepository.filterProducts(
                        category, supplier, minPrice, maxPrice, inStock)
                .stream()
                .map(product -> mapper.map(product, ProductDTO.class))
                .toList();
    }

    @Override
    public List<ProductDTO> sortByQuantityAsc() {
        return productRepository.findAllByOrderByQuantityAsc()
                .stream()
                .map(product -> mapper.map(product, ProductDTO.class)).toList();
    }

    @Override
    public List<ProductDTO> sortByQuantityDesc() {
        return productRepository.findAllByOrderByQuantityDesc()
                .stream()
                .map(product -> mapper.map(product, ProductDTO.class))
                .toList();
    }

    @Override
    public List<ProductDTO> filterProducts(FilterDTO filterDTO) {

        Map<String, Object> filters = filterDTO.getFilters();

        String category = (String) filters.get("category");
        String supplier = (String) filters.get("supplier");
        Double minPrice = filters.get("minPrice") != null ? ((Number) filters.get("minPrice")).doubleValue() : null;
        Double maxPrice = filters.get("maxPrice") != null ? ((Number) filters.get("maxPrice")).doubleValue() : null;
        Boolean inStock = (Boolean) filters.get("inStock");

        return productRepository.filterProducts(category, supplier, minPrice, maxPrice, inStock)
                .stream()
                .map(product -> mapper.map(product, ProductDTO.class))
                .toList();
    }
}
