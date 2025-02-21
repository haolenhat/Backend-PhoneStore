package com.example.backend.services.orderService;

import com.example.backend.entities.Order.ThongTinKhachHang;

import java.util.List;

public interface ThongTinKhachHangService {
    ThongTinKhachHang addThongTinKhachHang(ThongTinKhachHang thongTinKhachHang);
    ThongTinKhachHang updateThongTinKhachHang(int idKh, ThongTinKhachHang thongTinKhachHang);
    List<ThongTinKhachHang> getThongTinKhachHangByIdKh(int idKh);
    void deleteThongTinKhachHang(int idThongTin);

}