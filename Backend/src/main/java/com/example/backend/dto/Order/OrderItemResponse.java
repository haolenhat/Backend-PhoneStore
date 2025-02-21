package com.example.backend.dto.Order;

import java.math.BigDecimal;

public class OrderItemResponse {
    private String tenSanPham;
    private int soLuong;
    private BigDecimal donGia;
    private String mauSac;  // Thêm thuộc tính này
    private String LinkMauAnhSP;
    private  String dungLuong;
    // Constructors
    public OrderItemResponse() {}

    public OrderItemResponse(String tenSanPham, int soLuong, BigDecimal donGia, String mauSac) {
        this.tenSanPham = tenSanPham;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.mauSac = mauSac;
    }

    // Getter và Setter


    public String getDungLuong() {
        return dungLuong;
    }

    public void setDungLuong(String dungLuong) {
        this.dungLuong = dungLuong;
    }

    public String getLinkMauAnhSP() {
        return LinkMauAnhSP;
    }

    public void setLinkMauAnhSP(String linkMauAnhSP) {
        LinkMauAnhSP = linkMauAnhSP;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public BigDecimal getDonGia() {
        return donGia;
    }

    public void setDonGia(BigDecimal donGia) {
        this.donGia = donGia;
    }

    public String getMauSac() {
        return mauSac;
    }

    public void setMauSac(String mauSac) {
        this.mauSac = mauSac;
    }
}
