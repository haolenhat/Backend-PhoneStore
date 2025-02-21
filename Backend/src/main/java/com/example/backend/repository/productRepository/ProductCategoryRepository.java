package com.example.backend.repository.productRepository;



import com.example.backend.entities.product.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
    ProductCategory findByTenDm(String tenDm);
    Optional<ProductCategory> findByidDm(Integer idDm);
}
