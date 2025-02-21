package com.example.backend.services.orderService;

import com.example.backend.entities.Order.ThongTinKhachHang;
import com.example.backend.repository.orderRepository.ThongTinKhachHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ThongTinKhachHangServiceImpl implements ThongTinKhachHangService {

    @Autowired
    private ThongTinKhachHangRepository thongTinKhachHangRepository;

    @Override
    @Transactional
    public ThongTinKhachHang addThongTinKhachHang(ThongTinKhachHang thongTinKhachHang) {
        return thongTinKhachHangRepository.save(thongTinKhachHang);
    }

    @Override
    public ThongTinKhachHang updateThongTinKhachHang(int idThongTin, ThongTinKhachHang thongTinKhachHang)
    {
        ThongTinKhachHang existingThongTinKhachHang = thongTinKhachHangRepository.findById(idThongTin).orElse(null);
        if (existingThongTinKhachHang != null) {
            existingThongTinKhachHang.setTenNguoiNhan(thongTinKhachHang.getTenNguoiNhan()); // Đảm bảo cập nhật tên người nhận
            existingThongTinKhachHang.setDiaChi(thongTinKhachHang.getDiaChi());
            existingThongTinKhachHang.setTinhThanh(thongTinKhachHang.getTinhThanh());
            existingThongTinKhachHang.setQuanHuyen(thongTinKhachHang.getQuanHuyen());
            existingThongTinKhachHang.setPhuongXa(thongTinKhachHang.getPhuongXa());
            existingThongTinKhachHang.setSdtKh(thongTinKhachHang.getSdtKh());
            existingThongTinKhachHang.setGhiChu(thongTinKhachHang.getGhiChu());
            return thongTinKhachHangRepository.save(existingThongTinKhachHang); // Lưu thay đổi vào cơ sở dữ liệu
        }
        return null;
    }

    @Override
    public List<ThongTinKhachHang> getThongTinKhachHangByIdKh(int idKh) {
        return (List<ThongTinKhachHang>) thongTinKhachHangRepository.findByIdKh(idKh);
    }

    @Override
    @Transactional
    public void deleteThongTinKhachHang(int idThongTin)
    {
        thongTinKhachHangRepository.deleteById(idThongTin);
    }

}
