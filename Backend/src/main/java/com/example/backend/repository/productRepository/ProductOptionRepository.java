package com.example.backend.repository.productRepository;

import com.example.backend.entities.product.Product;
import com.example.backend.entities.product.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductOptionRepository extends JpaRepository<ProductOption, Integer> {
    List<ProductOption> findByProductIdSp(Integer idSp);
    List<ProductOption> findByProduct_IdSp(Integer idSp);
    List<ProductOption> findByIdTuyChon(Integer idTuyChon);
     ProductOption findByMauSacAndDungLuongAndProduct(String mauSac, String dungLuong, Product product);
}
