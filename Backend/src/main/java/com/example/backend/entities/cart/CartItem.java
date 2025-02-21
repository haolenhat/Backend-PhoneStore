package com.example.backend.entities.cart;

import com.example.backend.entities.product.ProductOption;
import com.example.backend.entities.user.User;
import jakarta.persistence.*;
import lombok.Data;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "tbl_gio_hang")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idGioHang;

    @ManyToOne
    @JoinColumn(name = "id_kh", referencedColumnName = "id_kh")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_tuy_chon")
    private ProductOption productOption;

    private Integer soLuong;
    private Timestamp ngayTao;

    public Integer getIdGioHang() {
        return idGioHang;
    }

    public void setIdGioHang(Integer idGioHang) {
        this.idGioHang = idGioHang;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ProductOption getProductOption() {
        return productOption;
    }

    public void setProductOption(ProductOption productOption) {
        this.productOption = productOption;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public Timestamp getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Timestamp ngayTao) {
        this.ngayTao = ngayTao;
    }
}
