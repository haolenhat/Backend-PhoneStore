package com.example.backend.dto.Product;

import com.example.backend.entities.product.ProductCategory;
import jakarta.persistence.Column;

import java.util.List;

public class ProductDTO {
    private int idSp;
    private String tenSp;
    private String thongTinSp;
    private String linkAnh;

    private Double trongLuong;

    private String manHinh;

    private String boNho;

    private String theSim;

    private String heDieuHanh;

    private String congSac;

    private String camera;

    private String baoMat;

    private String pin;

    private String baoHanh;


    private Double soSaoTrungBinh;
    private Integer soLuotDanhGia;
    private Integer soLuotBinhLuan;

    private List<ProductOptionDTO> productOptions;

    private ProductCategoryDTO productCategory;

    // Getters và setters


    public ProductCategoryDTO getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategoryDTO productCategory) {
        this.productCategory = productCategory;
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

    public int getIdSp() {
        return idSp;
    }

    public void setIdSp(int idSp) {
        this.idSp = idSp;
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

    public List<ProductOptionDTO> getProductOptions() {
        return productOptions;
    }

    public void setProductOptions(List<ProductOptionDTO> productOptions) {
        this.productOptions = productOptions;
    }
}
