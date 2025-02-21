package com.example.backend.repository.orderRepository;
import com.example.backend.entities.Order.TinhTrang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TinhTrangRepository extends JpaRepository<TinhTrang, Integer> {
    // Phương thức tìm trạng thái đơn hàng theo tên
    Optional<TinhTrang> findByTinhTrang(String tinhTrang);

}
