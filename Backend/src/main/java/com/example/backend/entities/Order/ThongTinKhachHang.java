package com.example.backend.entities.Order;

import jakarta.persistence.*;
import org.hibernate.boot.jaxb.hbm.internal.GenerationTimingConverter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_thong_tin_kh")
public class ThongTinKhachHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idThongTin;
    private int idKh;
    private String tenNguoiNhan;
    private String diaChi;
    private String tinhThanh;
    private String quanHuyen;
    private String phuongXa;
    private String sdtKh;
    private String ghiChu;
    @Column(name = "ngay_cap_nhat", nullable = false, updatable = false, insertable = false)
    private LocalDateTime ngayCapNhat;

    @PrePersist
    @PreUpdate
    public void prePersistAndUpdate()
    { this.ngayCapNhat = LocalDateTime.now(); }

    public int getIdThongTin() {
        return idThongTin;
    }

    public void setIdThongTin(int idThongTin) {
        this.idThongTin = idThongTin;
    }

    public int getIdKh() {
        return idKh;
    }

    public void setIdKh(int idKh) {
        this.idKh = idKh;
    }

    public String getTenNguoiNhan() {
        return tenNguoiNhan;
    }

    public void setTenNguoiNhan(String tenNguoiNhan) {
        this.tenNguoiNhan = tenNguoiNhan;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getTinhThanh() {
        return tinhThanh;
    }

    public void setTinhThanh(String tinhThanh) {
        this.tinhThanh = tinhThanh;
    }

    public String getQuanHuyen() {
        return quanHuyen;
    }

    public void setQuanHuyen(String quanHuyen) {
        this.quanHuyen = quanHuyen;
    }

    public String getPhuongXa() {
        return phuongXa;
    }

    public void setPhuongXa(String phuongXa) {
        this.phuongXa = phuongXa;
    }

    public String getSdtKh() {
        return sdtKh;
    }

    public void setSdtKh(String sdtKh) {
        this.sdtKh = sdtKh;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public LocalDateTime getNgayCapNhat() {
        return ngayCapNhat;
    }

    public void setNgayCapNhat(LocalDateTime ngayCapNhat) {
        this.ngayCapNhat = ngayCapNhat;
    }
}
