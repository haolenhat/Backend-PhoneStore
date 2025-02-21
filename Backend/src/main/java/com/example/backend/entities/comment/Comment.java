package com.example.backend.entities.comment;

import com.example.backend.entities.product.Product;
import com.example.backend.entities.user.User;
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "tbl_binh_luan")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idBinhLuan;

    @ManyToOne
    @JoinColumn(name = "id_kh")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_Sp")
    private Product product;

    @Column(name = "noi_dung", columnDefinition = "TEXT")
    private String noiDung;

    @Column(name = "danh_gia_sao")
    private Integer danhGiaSao;

    @Column(name = "thoi_gian_binh_luan", columnDefinition = "TIMESTAMP")
    private Timestamp thoiGianBinhLuan;

    @PrePersist
    protected void onCreate() {
        this.thoiGianBinhLuan = Timestamp.from(Instant.now());
    }

    // Getters và Setters
    public Integer getIdBinhLuan() {
        return idBinhLuan;
    }

    public void setIdBinhLuan(Integer idBinhLuan) {
        this.idBinhLuan = idBinhLuan;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public Integer getDanhGiaSao() {
        return danhGiaSao;
    }

    public void setDanhGiaSao(Integer danhGiaSao) {
        this.danhGiaSao = danhGiaSao;
    }

    public Timestamp getThoiGianBinhLuan() {
        return thoiGianBinhLuan;
    }

    public void setThoiGianBinhLuan(Timestamp thoiGianBinhLuan) {
        this.thoiGianBinhLuan = thoiGianBinhLuan;
    }
}
