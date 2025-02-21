package com.example.backend.repository.orderRepository;

import com.example.backend.entities.Order.ThongTinKhachHang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ThongTinKhachHangRepository extends JpaRepository<ThongTinKhachHang, Integer> {
    List<ThongTinKhachHang> findByIdKh(int idKh);
    Optional<ThongTinKhachHang> findById(int idThongTin);
}
