package com.example.backend.entities.product;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
@Table(name = "tbl_sanpham")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_dm")
    @JsonBackReference
    private ProductCategory productCategory;

    @Column(name = "ten_sp")
    private String tenSp;

    @Column(name = "thong_tin_sp")
    private String thongTinSp;

    @Column(name = "link_anh")
    private String linkAnh;

    @Column(name = "trong_luong")
    private Double trongLuong;

    @Column(name = "man_hinh")
    private String manHinh;

    @Column(name = "bo_nho")
    private String boNho;

    @Column(name = "the_sim")
    private String theSim;

    @Column(name = "he_dieu_hanh")
    private String heDieuHanh;

    @Column(name = "cong_sac")
    private String congSac;

    @Column(name = "camera")
    private String camera;

    @Column(name = "bao_mat")
    private String baoMat;

    @Column(name = "pin")
    private String pin;

    @Column(name = "bao_hanh")
    private String baoHanh;

    @Column(name = "so_sao_trung_binh")
    private Double soSaoTrungBinh;

    @Column(name = "so_luot_danh_gia")
    private Integer soLuotDanhGia;

    @Column(name = "so_luot_binh_luan")
    private Integer soLuotBinhLuan;

    @Column(name = "ngay_tao", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp ngayTao;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<ProductOption> productOptions;

    // Getters and Setters
    public Integer getIdSp() {
        return idSp;
    }

    public void setIdSp(Integer idSp) {
        this.idSp = idSp;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public String getTenSp() {
        return tenSp;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }

    public String getThongTinSp() {
        return thongTinSp;
    }

    public void setThongTinSp(String thongTinSp) {
        this.thongTinSp = thongTinSp;
    }

    public String getLinkAnh() {
        return linkAnh;
    }

    public void setLinkAnh(String linkAnh) {
        this.linkAnh = linkAnh;
    }

    public Double getTrongLuong() {
        return trongLuong;
    }

    public void setTrongLuong(Double trongLuong) {
        this.trongLuong = trongLuong;
    }

    public String getManHinh() {
        return manHinh;
    }

    public void setManHinh(String manHinh) {
        this.manHinh = manHinh;
    }

    public String getBoNho() {
        return boNho;
    }

    public void setBoNho(String boNho) {
        this.boNho = boNho;
    }

    public String getTheSim() {
        return theSim;
    }

    public void setTheSim(String theSim) {
        this.theSim = theSim;
    }

    public String getHeDieuHanh() {
        return heDieuHanh;
    }

    public void setHeDieuHanh(String heDieuHanh) {
        this.heDieuHanh = heDieuHanh;
    }

    public String getCongSac() {
        return congSac;
    }

    public void setCongSac(String congSac) {
        this.congSac = congSac;
    }

    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    public String getBaoMat() {
        return baoMat;
    }

    public void setBaoMat(String baoMat) {
        this.baoMat = baoMat;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getBaoHanh() {
        return baoHanh;
    }

    public void setBaoHanh(String baoHanh) {
        this.baoHanh = baoHanh;
    }

    public Double getSoSaoTrungBinh() {
        return soSaoTrungBinh;
    }

    public void setSoSaoTrungBinh(Double soSaoTrungBinh) {
        this.soSaoTrungBinh = soSaoTrungBinh;
    }

    public Integer getSoLuotDanhGia() {
        return soLuotDanhGia;
    }

    public void setSoLuotDanhGia(Integer soLuotDanhGia) {
        this.soLuotDanhGia = soLuotDanhGia;
    }

    public Integer getSoLuotBinhLuan() {
        return soLuotBinhLuan;
    }

    public void setSoLuotBinhLuan(Integer soLuotBinhLuan) {
        this.soLuotBinhLuan = soLuotBinhLuan;
    }

    public List<ProductOption> getProductOptions() {
        return productOptions;
    }

    public void setProductOptions(List<ProductOption> productOptions) {
        this.productOptions = productOptions;
    }

    public Timestamp getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Timestamp ngayTao) {
        this.ngayTao = ngayTao;
    }
}