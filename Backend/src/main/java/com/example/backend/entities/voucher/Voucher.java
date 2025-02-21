package com.example.backend.entities.voucher;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "tbl_voucher")
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idVoucher;

    @Column(unique = true, nullable = false)
    private String maVoucher;

    @Column(columnDefinition = "TEXT")
    private String moTa;

    @Column(nullable = false)
    private BigDecimal giaTri;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VoucherType loaiVoucher;

    @Column(nullable = false)
    private Date ngayBatDau;

    @Column(nullable = false)
    private Date ngayKetThuc;

    @Column(nullable = false)
    private BigDecimal dieuKienSuDung = BigDecimal.ZERO; // Default value set programmatically

    @Column(nullable = false)
    private int soLuong = 0; // Default value set programmatically

    // Getters và Setters

    // Enum cho loại voucher
    public enum VoucherType {
        PERCENT,
        AMOUNT
    }

    public int getIdVoucher() {
        return idVoucher;
    }

    public void setIdVoucher(int idVoucher) {
        this.idVoucher = idVoucher;
    }

    public String getMaVoucher() {
        return maVoucher;
    }

    public void setMaVoucher(String maVoucher) {
        this.maVoucher = maVoucher;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public BigDecimal getGiaTri() {
        return giaTri;
    }

    public void setGiaTri(BigDecimal giaTri) {
        this.giaTri = giaTri;
    }

    public VoucherType getLoaiVoucher() {
        return loaiVoucher;
    }

    public void setLoaiVoucher(VoucherType loaiVoucher) {
        this.loaiVoucher = loaiVoucher;
    }

    public Date getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(Date ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public BigDecimal getDieuKienSuDung() {
        return dieuKienSuDung;
    }

    public void setDieuKienSuDung(BigDecimal dieuKienSuDung) {
        this.dieuKienSuDung = dieuKienSuDung;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
