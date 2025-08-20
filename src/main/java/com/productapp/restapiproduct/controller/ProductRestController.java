package com.productapp.restapiproduct.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.productapp.restapiproduct.entity.Product;
import com.productapp.restapiproduct.entity.ProductDTO;
import com.productapp.restapiproduct.entity.UserDTO;
import com.productapp.restapiproduct.service.ProductService;
import com.productapp.restapiproduct.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/products")
public class ProductRestController {

    private static final Logger logger = LoggerFactory.getLogger(ProductRestController.class);
    private final ProductService productService;


    public ProductRestController(ProductService productService) {
        this.productService = productService;

    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        logger.warn("Received request at /hello");
        return ResponseEntity.ok("Hello from Product REST API!");
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        logger.info("Fetching all products");
        List<ProductDTO> products = productService.getAllProducts();

        if (products.isEmpty()) {
            logger.warn("No products found");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(products);
        } else {
            logger.info("Total products fetched: {}", products.size());
            return ResponseEntity.ok(products);
        }
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable int productId) {
        logger.info("Fetching product with ID: {}", productId);
        Product product = productService.findById(productId);

        if (product != null) {
            logger.debug("Product found: {}", product);
            return ResponseEntity.ok(product);
        } else {
            logger.warn("Product with ID {} not found", productId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<Product> saveProduct(@Valid @RequestBody ProductDTO productDTO) {
        logger.info("Saving product: {}", productDTO);
        Product savedProduct = productService.save(productDTO);
        logger.info("Product saved with ID: {}", savedProduct.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }


    //    Write delete mapping which returns a message of succesful deletion
    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable int productId) {
        logger.info("Deleting product with ID: {}", productId);
        productService.deleteById(productId);
        logger.info("Product with ID {} deleted successfully", productId);
        return ResponseEntity.ok("Product deleted successfully");
    }



}
