package com.example.backend.repository.orderRepository;

import com.example.backend.entities.Order.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    List<OrderDetail> findByIdDdh(int idDdh);
    List<OrderDetail> findByTuyChonSanPham_Product_IdSp(Integer idSp);
}
