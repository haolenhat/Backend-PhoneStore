package com.example.backend.entities.product;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tbl_tuy_chon_sanpham")
public class ProductOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTuyChon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sp")
    @JsonBackReference
    private Product product;

    @Column(name = "mau_sac")
    private String mauSac;

    @Column(name = "link_mau_anh_sp")
    private String linkMauAnhSp;

    @Column(name = "dung_luong")
    private String dungLuong;

    @Column(name = "gia_sp")
    private Double giaSp;

    @Column(name = "khuyen_mai")
    private Double khuyenMai;


    @Column(name = "so_luong")
    private Integer soLuong;

    // Getters and Setters
    public Integer getIdTuyChon() {
        return idTuyChon;
    }

    public void setIdTuyChon(Integer idTuyChon) {
        this.idTuyChon = idTuyChon;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getMauSac() {
        return mauSac;
    }

    public void setMauSac(String mauSac) {
        this.mauSac = mauSac;
    }

    public String getLinkMauAnhSp() {
        return linkMauAnhSp;
    }

    public void setLinkMauAnhSp(String linkMauAnhSp) {
        this.linkMauAnhSp = linkMauAnhSp;
    }

    public String getDungLuong() {
        return dungLuong;
    }

    public void setDungLuong(String dungLuong) {
        this.dungLuong = dungLuong;
    }

    public Double getGiaSp() {
        return giaSp;
    }

    public void setGiaSp(Double giaSp) {
        this.giaSp = giaSp;
    }

    public Double getKhuyenMai() {
        return khuyenMai;
    }

    public void setKhuyenMai(Double khuyenMai) {
        this.khuyenMai = khuyenMai;
    }


    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }
}
