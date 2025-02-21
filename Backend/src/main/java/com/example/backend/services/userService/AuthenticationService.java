package com.example.backend.services.userService;

import com.example.backend.dto.JWT.JwtAuthenticationResponse;
import com.example.backend.dto.JWT.RefreshTokenRequest;
import com.example.backend.dto.Login.SignInRequest;
import com.example.backend.dto.Login.SignUpRequest;
import com.example.backend.entities.user.User;

public interface AuthenticationService {
    User signUp(SignUpRequest signUpRequest);
    JwtAuthenticationResponse signin(SignInRequest signInRequest);
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
