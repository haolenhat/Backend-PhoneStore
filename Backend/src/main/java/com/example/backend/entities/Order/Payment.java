package com.example.backend.entities.Order;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "tbl_thanh_toan")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idThanhToan;

    private int idDdh;
    private String phuongThuc;
    private BigDecimal soTien;
    private Timestamp ngayThanhToan;

    public int getIdThanhToan() {
        return idThanhToan;
    }

    public void setIdThanhToan(int idThanhToan) {
        this.idThanhToan = idThanhToan;
    }

    public int getIdDdh() {
        return idDdh;
    }

    public void setIdDdh(int idDdh) {
        this.idDdh = idDdh;
    }

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

    public Timestamp getNgayThanhToan() {
        return ngayThanhToan;
    }

    public void setNgayThanhToan(Timestamp ngayThanhToan) {
        this.ngayThanhToan = ngayThanhToan;
    }


}
