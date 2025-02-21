package com.example.backend.entities.user;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "tbl_nguoidung")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_kh")
    private Integer idKh;

    @Column(name = "mat_khau")
    private String matKhau;

    @Column(name = "ten_kh", length = 250)
    private String tenKh;

    @Enumerated(EnumType.STRING)
    @Column(name = "vai_tro", columnDefinition = "ENUM('USER', 'ADMIN') default 'USER'")
    private Role role = Role.USER;

    @Column(name = "mail", length = 50, unique = true, nullable = false)
    private String mail;

    @Column(name = "google_id", unique = true)
    private String googleId;

    @CreationTimestamp
    @Column(name = "ngay_tao", updatable = false)
    private LocalDateTime ngayTao;

    @Column(name = "reset_token", unique = true)
    private String resetToken;


    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return matKhau;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return mail;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
