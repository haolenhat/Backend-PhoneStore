package com.example.backend.repository.userRepository;

import com.example.backend.entities.user.User;
import com.example.backend.entities.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {


    Optional<User> findByMail(String mail);

    User findByRole(Role role);

    List<User> findByTenKhContainingIgnoreCase(String tenKh);


    Optional<User> findByIdKh(int idKh);

    void deleteByIdKh(Integer idKh);

    List<User> findAllByRole(Role role);

    Optional<User> findByResetToken(String resetToken);

}
