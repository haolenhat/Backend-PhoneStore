package com.example.backend.entities.Order;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_tinh_trang")
public class TinhTrang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tinh_trang")
    private int idTinhTrang;

    @Column(name = "tinh_trang")
    private String tinhTrang;

    // Getter và Setter
    public int getIdTinhTrang() {
        return idTinhTrang;
    }

    public void setIdTinhTrang(int idTinhTrang) {
        this.idTinhTrang = idTinhTrang;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }


}
