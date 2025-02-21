package com.example.backend.services.userService;

import com.example.backend.entities.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    UserDetailsService userDetailsServices();
    List<User> getAllUser();
    List<User> searchUsers(String query);
    public User updateUserDetails(User updatedUser);
    public void deleteUserId(Integer idKh);
    User findUserByIdKh(Integer idKh);
    User updateUserByIdKh(Integer idKh, User updatedUser);
    void resetPassword(String token, String newPassword);
    void processForgotPassword(String email) throws jakarta.mail.MessagingException;
}
