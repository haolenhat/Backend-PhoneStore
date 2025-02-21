package com.example.backend.dto.Login;

import com.example.backend.entities.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data

public class SignUpRequest {

    private String tenKh;

    private String mail;

    private String matKhau;


}
