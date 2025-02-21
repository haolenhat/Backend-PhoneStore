package com.example.backend.dto.Product;

public class ProductOptionDTO {
    private Integer idTuyChon;
    private String mauSac;
    private String linkMauAnhSp;
    private String dungLuong;
    private Double giaSp;
    private Double khuyenMai;
    private Integer soLuong;
    private Double giaSauKhuyenMai;

    public Double getGiaSauKhuyenMai() {
        return giaSp * (1 - khuyenMai / 100);
    }

    // Getters và setters
    public Integer getIdTuyChon() {
        return idTuyChon;
    }

    public void setIdTuyChon(Integer idTuyChon) {
        this.idTuyChon = idTuyChon;
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

    public void setGiaSauKhuyenMai(Double giaSauKhuyenMai) {
        this.giaSauKhuyenMai = giaSauKhuyenMai;
    }
}
