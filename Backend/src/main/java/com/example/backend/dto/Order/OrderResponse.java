package com.example.backend.dto.Order;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderResponse {
    private int idDdh;
    private int idKh;
    private String maDonHang;
    private String tenKhachHang;
    private BigDecimal tongGia;
    private BigDecimal phiShip;
    private Date ngayLap;
    private Integer idVoucher;
    private String tenNguoiNhan;
    private String diaChi;
    private String tinhThanh;
    private String quanHuyen;
    private String phuongXa;
    private String sdtKh;
    private List<OrderItemResponse> items;
    private PaymentResponse payment;
    private TinhTrangResponse tinhTrang; // Cập nhật thành đối tượng TinhTrangResponse

    public static class TinhTrangResponse
    { private int idTinhTrang;
    private String tinhTrang;

        public int getIdTinhTrang() {
            return idTinhTrang;
        }

        public void setIdTinhTrang(int idTinhTrang) {
            this.idTinhTrang = idTinhTrang;
        }

        public String getTinhTrang() {
            return tinhTrang;
        }

        public void setTinhTrang(String tinhTrang) {
            this.tinhTrang = tinhTrang;
        }
    }

    public TinhTrangResponse getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(TinhTrangResponse tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public static class PaymentResponse {
        private String phuongThuc;
        private BigDecimal soTien;

        public String getPhuongThuc() {
            return phuongThuc;
        }

        public void setPhuongThuc(String phuongThuc) {
            this.phuongThuc = phuongThuc;
        }

        public BigDecimal getSoTien() {
            return soTien;
        }

        public void setSoTien(BigDecimal soTien) {
            this.soTien = soTien;
        }
    }


    public Date getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(Date ngayLap) {
        this.ngayLap = ngayLap;
    }

    public int getIdKh() {
        return idKh;
    }

    public void setIdKh(int idKh) {
        this.idKh = idKh;
    }

    public int getIdDdh() {
        return idDdh;
    }

    public void setIdDdh(int idDdh) {
        this.idDdh = idDdh;
    }

    public String getMaDonHang() {
        return maDonHang;
    }

    public void setMaDonHang(String maDonHang) {
        this.maDonHang = maDonHang;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public BigDecimal getTongGia() {
        return tongGia;
    }

    public void setTongGia(BigDecimal tongGia) {
        this.tongGia = tongGia;
    }

    public BigDecimal getPhiShip() {
        return phiShip;
    }

    public void setPhiShip(BigDecimal phiShip) {
        this.phiShip = phiShip;
    }

    public Integer getIdVoucher() {
        return idVoucher;
    }

    public void setIdVoucher(Integer idVoucher) {
        this.idVoucher = idVoucher;
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

    public List<OrderItemResponse> getItems() {
        return items;
    }

    public void setItems(List<OrderItemResponse> items) {
        this.items = items;
    }

    public PaymentResponse getPayment() {
        return payment;
    }

    public void setPayment(PaymentResponse payment) {
        this.payment = payment;
    }

}
