package com.example.backend.entities.Order;

import com.example.backend.entities.user.User;
import com.example.backend.entities.voucher.Voucher;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "tbl_don_dh")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDdh;
    private int idKh;

    @Column(name = "idTinhTrang", insertable = false, updatable = false)
    private Integer idTinhTrang;
    private Date ngayLap;
    private String maDonHang;
    private BigDecimal phiShip;
    private BigDecimal tongGia;
    private Integer idVoucher;
    private String tenNguoiNhan;
    private String diaChi;
    private String tinhThanh;
    private String quanHuyen;
    private String phuongXa;
    private String sdtKh;

    @ManyToOne
    @JoinColumn(name = "idKh", insertable = false, updatable = false)
    private User khachHang;

    @ManyToOne
    @JoinColumn(name = "idTinhTrang")
    private TinhTrang tinhTrang;

    @ManyToOne
    @JoinColumn(name = "idVoucher", insertable = false, updatable = false)
    private Voucher voucher;

    // Getters and Setters


    public int getIdTinhTrang() {
        return idTinhTrang;
    }

    public void setIdTinhTrang(int idTinhTrang) {
        this.idTinhTrang = idTinhTrang;
    }

    public int getIdDdh() {
        return idDdh;
    }

    public void setIdDdh(int idDdh) {
        this.idDdh = idDdh;
    }

    public int getIdKh() {
        return idKh;
    }

    public void setIdKh(int idKh) {
        this.idKh = idKh;
    }

    public Date getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(Date ngayLap) {
        this.ngayLap = ngayLap;
    }

    public String getMaDonHang() {
        return maDonHang;
    }

    public void setMaDonHang(String maDonHang) {
        this.maDonHang = maDonHang;
    }

    public BigDecimal getPhiShip() {
        return phiShip;
    }

    public void setPhiShip(BigDecimal phiShip) {
        this.phiShip = phiShip;
    }

    public BigDecimal getTongGia() {
        return tongGia;
    }

    public void setTongGia(BigDecimal tongGia) {
        this.tongGia = tongGia;
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

    public User getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(User khachHang) {
        this.khachHang = khachHang;
    }

    public TinhTrang getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(TinhTrang tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    @Override
    public String toString() {
        return "Thông tin đơn hàng mới:\n\n" +
                "Mã đơn hàng: " + maDonHang + "\n" +
                "Tổng giá trị đơn hàng: " + tongGia + "\n" +
                "Tên người nhận: " + tenNguoiNhan + "\n" +
                "Địa chỉ: " + diaChi + "\n" +
                "SĐT: " + sdtKh + "\n" +
                "Mã voucher: " + voucher + "\n";
    }

}
