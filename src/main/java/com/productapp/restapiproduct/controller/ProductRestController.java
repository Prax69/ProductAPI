package com.productapp.restapiproduct.controller;

import com.productapp.restapiproduct.entity.FilterDTO;
import com.productapp.restapiproduct.entity.Product;
import com.productapp.restapiproduct.entity.ProductDTO;
import com.productapp.restapiproduct.service.ProductService;
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

//    @GetMapping("/hello")
//    public ResponseEntity<String> hello() {
//        logger.warn("Received request at /hello");
//        return ResponseEntity.ok("Hello from Product REST API!");
//    }

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

    @PutMapping
    public ResponseEntity<Product> updateProduct(@Valid @RequestBody ProductDTO productDTO) {
        logger.info("Updating product: {}", productDTO);
        Product updatedProduct = productService.save(productDTO);
        logger.info("Product updated with ID: {}", updatedProduct.getId());
        return ResponseEntity.ok(updatedProduct);
    }

    //    Write delete mapping which returns a message of succesful deletion
    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable int productId) {
        logger.info("Deleting product with ID: {}", productId);
        productService.deleteById(productId);
        logger.info("Product with ID {} deleted successfully", productId);
        return ResponseEntity.ok("Product deleted successfully");
    }


    @GetMapping(value = "/sort", params = {"sortBy"})
    public ResponseEntity<List<ProductDTO>> sortProductsByPriceAsc(@RequestParam String sortBy) {
        logger.info("Sorting products by: {}", sortBy);
        List<ProductDTO> sortedProducts;

        switch (sortBy) {
            case "priceAsc":
                sortedProducts = productService.sortByPriceAsc();
                break;
            case "priceDesc":
                sortedProducts = productService.sortByPriceDesc();
                break;
            case "quantityAsc":
                sortedProducts = productService.sortByQuantityAsc();
                break;
            case "quantityDesc":
                sortedProducts = productService.sortByQuantityDesc();
                break;
            default:
                logger.warn("Invalid sort parameter: {}", sortBy);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if (sortedProducts.isEmpty()) {
            logger.warn("No products found to sort by: {}", sortBy);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(sortedProducts);
        } else {
            logger.info("Total products sorted by {}: {}", sortBy, sortedProducts.size());
            return ResponseEntity.ok(sortedProducts);
        }
    }

    //    Apply filtering on the product list based on the price range
    @GetMapping(value = "/filter", params = {"minPrice", "maxPrice"})
    public ResponseEntity<List<ProductDTO>> filterProductsByPriceRange(
            @RequestParam double minPrice, @RequestParam double maxPrice) {
        logger.info("Filtering products by price range: {} - {}", minPrice, maxPrice);
        List<ProductDTO> filteredProducts = productService.filterByPriceRange(minPrice, maxPrice);

        if (filteredProducts.isEmpty()) {
            logger.warn("No products found in the specified price range");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(filteredProducts);
        } else {
            logger.info("Total products found in the specified price range: {}", filteredProducts.size());
            return ResponseEntity.ok(filteredProducts);
        }
    }

    @GetMapping(value = "/filter", params = {"minQuantity"})
    public ResponseEntity<List<ProductDTO>> filterProductsByQuantity(
            @RequestParam double minQuantity) {
        logger.info("Filtering products by minimum quantity: {}", minQuantity);
        List<ProductDTO> filteredProducts = productService.filterByQuantity(minQuantity);
        if (filteredProducts.isEmpty()) {
            logger.warn("No products found with minimum quantity: {}", minQuantity);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(filteredProducts);
        } else {
            logger.info("Total products found with minimum quantity: {}", filteredProducts.size());
            return ResponseEntity.ok(filteredProducts);
        }
    }

//    @GetMapping(value = "/filter", params = {"category", "supplier", "minPrice", "maxPrice", "inStock"})
//    public ResponseEntity<List<ProductDTO>> filterProducts(
//            @RequestParam(required = false) String category,
//            @RequestParam(required = false) String supplier,
//            @RequestParam(required = false) Double minPrice,
//            @RequestParam(required = false) Double maxPrice,
//            @RequestParam(required = false) Boolean inStock) {
//        logger.info("Filtering products: category={}, supplier={}, minPrice={}, maxPrice={}, inStock={}",
//                category, supplier, minPrice, maxPrice, inStock);
//
//        List<ProductDTO> filteredProducts = productService.filterProducts(category, supplier, minPrice, maxPrice, inStock);
//
//        if (filteredProducts.isEmpty()) {
//            logger.warn("No products found matching filter criteria");
//            return ResponseEntity.noContent().build();
//        }
//
//        logger.info("Total products found: {}", filteredProducts.size());
//        return ResponseEntity.ok(filteredProducts);
//    }

    @GetMapping(value = "/filter")
    public ResponseEntity<List<ProductDTO>> filterProducts(@RequestBody
                                                           FilterDTO filterDTO) {
        logger.info("New way of Filtering products with DTO: {}", filterDTO);
        List<ProductDTO> filteredProducts = productService.filterProducts(filterDTO);
        if (filteredProducts.isEmpty()) {
            logger.warn("No products found matching filter criteria");
            return ResponseEntity.noContent().build();
        }
        logger.info("Total products found: {}", filteredProducts.size());
        return ResponseEntity.ok(filteredProducts);
    }
}
