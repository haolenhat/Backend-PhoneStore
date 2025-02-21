package com.example.backend.services.userService;

import com.example.backend.dto.JWT.JwtAuthenticationResponse;
import com.example.backend.dto.JWT.RefreshTokenRequest;
import com.example.backend.dto.Login.SignInRequest;
import com.example.backend.dto.Login.SignUpRequest;
import com.example.backend.entities.user.User;
import com.example.backend.repository.userRepository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationImpl implements AuthenticationService{

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JWTServices jwtServices;

    public User signUp(SignUpRequest signUpRequest){
        User user = new User();

        user.setMail(signUpRequest.getMail());
        user.setTenKh(signUpRequest.getTenKh());
        user.setMatKhau(passwordEncoder.encode(signUpRequest.getMatKhau()));

        return userRepository.save(user);
    }



    public JwtAuthenticationResponse signin(SignInRequest signInRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getMail(),signInRequest.getMatKhau()));

        var user = userRepository.findByMail(signInRequest.getMail()).orElseThrow(()-> new IllegalArgumentException("Invalid email or password !"));
        var jwt = jwtServices.generateToken(user);
        var refreshToken = jwtServices.generateRefreshToken(new HashMap<>(),user);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(jwt);
        return jwtAuthenticationResponse;
    }

    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest){
        String userEmail = jwtServices.extractUsserName(refreshTokenRequest.getToken());
        User user = userRepository.findByMail(userEmail).orElseThrow();
        if(jwtServices.isTokenValid(refreshTokenRequest.getToken(),user)){
            var jwt = jwtServices.generateToken(user);

            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());

            return jwtAuthenticationResponse;

        }
        return null;
    }

}
