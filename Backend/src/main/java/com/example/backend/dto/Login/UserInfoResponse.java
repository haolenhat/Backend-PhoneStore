package com.example.backend.dto.Login;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInfoResponse {
    private Integer idKh;
    private String tenKh;
    private String mail;
    private String googleId;
}
