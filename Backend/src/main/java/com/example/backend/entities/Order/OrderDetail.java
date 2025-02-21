package com.example.backend.entities.Order;

import com.example.backend.entities.product.ProductOption;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "tbl_ct_ddh")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCtDdh;

    private int idDdh;
    private int idTuyChon;
    private int soLuongMua;
    private BigDecimal donGia;

    @ManyToOne
    @JoinColumn(name = "idTuyChon", insertable = false, updatable = false)
    private ProductOption tuyChonSanPham;

    // Getter và Setter

    public int getIdCtDdh() {
        return idCtDdh;
    }

    public void setIdCtDdh(int idCtDdh) {
        this.idCtDdh = idCtDdh;
    }

    public int getIdDdh() {
        return idDdh;
    }

    public void setIdDdh(int idDdh) {
        this.idDdh = idDdh;
    }

    public int getIdTuyChon() {
        return idTuyChon;
    }

    public void setIdTuyChon(int idTuyChon) {
        this.idTuyChon = idTuyChon;
    }

    public int getSoLuongMua() {
        return soLuongMua;
    }

    public void setSoLuongMua(int soLuongMua) {
        this.soLuongMua = soLuongMua;
    }

    public BigDecimal getDonGia() {
        return donGia;
    }

    public void setDonGia(BigDecimal donGia) {
        this.donGia = donGia;
    }

    public ProductOption getTuyChonSanPham() {
        return tuyChonSanPham;
    }

    public void setTuyChonSanPham(ProductOption tuyChonSanPham) {
        this.tuyChonSanPham = tuyChonSanPham;
    }
}
