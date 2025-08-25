package com.productapp.restapiproduct.repository;

import com.productapp.restapiproduct.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {


    List<Product> findByPriceBetween(double minPrice, double maxPrice);

    List<Product> findAllByOrderByPriceAsc();

    List<Product> findAllByOrderByPriceDesc();

    List<Product> findByQuantityGreaterThanEqual(double minQuantity);

//    @Query("""
//        SELECT p FROM Product p
//        WHERE (:category IS NULL OR p.category = :category)
//        AND (:supplier IS NULL OR p.supplier = :supplier)
//        AND (:minPrice IS NULL OR p.price >= :minPrice)
//        AND (:maxPrice IS NULL OR p.price <= :maxPrice)
//        AND (:inStock IS NULL OR (:inStock = true AND p.quantity > 0))
//    """)
//    List<Product> filterProducts(
//            @Param("category") String category,
//            @Param("supplier") String supplier,
//            @Param("minPrice") Double minPrice,
//            @Param("maxPrice") Double maxPrice,
//            @Param("inStock") Boolean inStock
//    );

    List<Product> findAllByOrderByQuantityAsc();

    List<Product> findAllByOrderByQuantityDesc();


}
