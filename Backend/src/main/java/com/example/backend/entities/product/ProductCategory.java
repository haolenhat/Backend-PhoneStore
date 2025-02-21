package com.example.backend.entities.product;

import com.example.backend.entities.product.Product;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "tbl_dm_sanpham")
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDm;

    @Column(name = "ten_dm")
    private String tenDm;

    @OneToMany(mappedBy = "productCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Product> products = new ArrayList<>();

    public Integer getIdDm() {
        return idDm;
    }

    public void setIdDm(Integer idDm) {
        this.idDm = idDm;
    }

    public String getTenDm() {
        return tenDm;
    }

    public void setTenDm(String tenDm) {
        this.tenDm = tenDm;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }


}
