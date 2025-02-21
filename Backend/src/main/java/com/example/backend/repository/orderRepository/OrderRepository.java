package com.example.backend.repository.orderRepository;

import com.example.backend.entities.Order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Optional<Order> findByMaDonHang(String maDonHang);
    List<Order> findByIdKh(Integer idKh);
    Optional<List> findByIdDdh(int idDdh);
    @Query("SELECT CASE WHEN COUNT(od) > 0 THEN TRUE ELSE FALSE END FROM OrderDetail od WHERE od.tuyChonSanPham.product.idSp = :idSp")
    boolean existsByProductId(@Param("idSp") Integer idSp);
}
