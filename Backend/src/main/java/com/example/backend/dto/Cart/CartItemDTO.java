package com.example.backend.dto.Cart;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class CartItemDTO {
    private Integer idGioHang;
    private UserDTO user;
    private ProductOptionDTO productOption;
    private Integer soLuong;
    private Timestamp ngayTao;

    @Data
    public static class UserDTO {
        private Integer idKh;
        private String tenKh;
        private String role;
        private String mail;
    }

    @Data
    public static class ProductOptionDTO {
        private Integer idTuyChon;
        private String mauSac;
        private String dungLuong;
        private Double giaSp;
        private String tenSp;
        private String linkAnh;
    }
}
