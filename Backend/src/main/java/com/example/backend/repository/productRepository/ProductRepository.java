package com.example.backend.repository.productRepository;


import com.example.backend.entities.product.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @EntityGraph(attributePaths = {"productCategory", "productOptions"})
    List<Product> findAll();
    List<Product> findByTenSpContainingIgnoreCase(String name);
    List<Product> findTop10ByOrderByNgayTaoDesc();
    Optional<Product> findByIdSp(Integer idSp);
    @Query("SELECT p FROM Product p WHERE p.tenSp = :tenSp")
    List<Product> findByTenSp(@Param("tenSp") String tenSp);


}
