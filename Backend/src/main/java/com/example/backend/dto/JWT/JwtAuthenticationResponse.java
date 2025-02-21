package com.example.backend.dto.JWT;

import lombok.Data;

@Data

public class JwtAuthenticationResponse {
    private String token;

    private String refreshToken;
}
