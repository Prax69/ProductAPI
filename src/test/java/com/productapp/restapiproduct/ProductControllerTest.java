package com.productapp.restapiproduct;

import com.productapp.restapiproduct.controller.ProductRestController;
import com.productapp.restapiproduct.entity.ProductDTO;
import com.productapp.restapiproduct.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductRestController productController;

    private ProductDTO product1;
    private ProductDTO product2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);


        product1 = new ProductDTO(1, "Laptop", "Gaming Laptop", 1000, 10, "Electronics", "SupplierA");
        product2 = new ProductDTO(2, "Phone", "Smartphone", 500, 20, "Electronics", "SupplierB");
    }

    @Test
    void testSortProducts_ReturnsSortedPage() {
        // Arrange
        List<ProductDTO> products = Arrays.asList(product1, product2);
        Page<ProductDTO> productPage = new PageImpl<>(products);

        when(productService.getSortedProducts(eq("priceAsc"), any(Pageable.class)))
                .thenReturn(productPage);

        // Act
        ResponseEntity<Page<ProductDTO>> response = productController.sortProducts("priceAsc", 0, 2);

        // Assert
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody().getContent()).hasSize(2);
        assertThat(response.getBody().getContent().get(0).getName()).isEqualTo("Laptop");

        verify(productService, times(1)).getSortedProducts(eq("priceAsc"), any(Pageable.class));
    }

    @Test
    void testSortProducts_ReturnsNoContent_WhenEmpty() {
        // Arrange
        Page<ProductDTO> emptyPage = new PageImpl<>(Collections.emptyList());
        when(productService.getSortedProducts(eq("priceAsc"), any(Pageable.class)))
                .thenReturn(emptyPage);

        // Act
        ResponseEntity<Page<ProductDTO>> response = productController.sortProducts("priceAsc", 0, 2);

        // Assert
        assertThat(response.getStatusCodeValue()).isEqualTo(204); // NO_CONTENT
        assertThat(response.getBody().getContent()).isEmpty();

        verify(productService, times(1)).getSortedProducts(eq("priceAsc"), any(Pageable.class));
    }

    @Test
    void testSortProducts_InvalidSortBy_ShouldThrowException() {
        // Arrange
        when(productService.getSortedProducts(eq("invalid"), any(Pageable.class)))
                .thenThrow(new IllegalArgumentException("Invalid sort parameter"));

        try {
            productController.sortProducts("invalid", 0, 2);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).contains("Invalid sort parameter");
        }

        verify(productService, times(1)).getSortedProducts(eq("invalid"), any(Pageable.class));
    }
}
