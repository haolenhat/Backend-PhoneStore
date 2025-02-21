package com.example.backend.services.userService;

import com.example.backend.entities.user.User;
import com.example.backend.repository.userRepository.UserRepository;
import com.example.backend.services.emailService.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    @Override
    public UserDetailsService userDetailsServices() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                User user = userRepository.findByMail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
                return new org.springframework.security.core.userdetails.User(
                        user.getMail(),
                        user.getMatKhau(),
                        Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name()))
                );
            }
        };
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    //User qua ký tự
    public List<User> searchUsers(String query) {
        return userRepository.findByTenKhContainingIgnoreCase(query);
    }

    @Override
    public User findUserByIdKh(Integer idKh) {
        return userRepository.findByIdKh(idKh)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + idKh));
    }


    // hàm sửa thông tin user qua id

    @Override
    public User updateUserDetails(User updatedUser) {
        User user = userRepository.findByIdKh(updatedUser.getIdKh()).orElse(null);
        if (user != null) {
            user.setTenKh(updatedUser.getTenKh());
            user.setMail(updatedUser.getMail());
            user.setRole(updatedUser.getRole());
            if (updatedUser.getMatKhau() != null && !updatedUser.getMatKhau().isEmpty()) {
                user.setMatKhau(passwordEncoder.encode(updatedUser.getMatKhau()));
            }
            return userRepository.save(user);
        }
        return null;
    }


    @Override
    public User updateUserByIdKh(Integer idKh, User updatedUser) {
        // Tìm người dùng dựa trên idKh
        User user = userRepository.findByIdKh(idKh).orElseThrow(() ->
                new RuntimeException("User not found with id: " + idKh)
        );

        // Cập nhật các thông tin của người dùng
        user.setTenKh(updatedUser.getTenKh());
        user.setMail(updatedUser.getMail());
        user.setRole(updatedUser.getRole());

        // Kiểm tra mật khẩu và mã hóa nếu có thay đổi
        if (updatedUser.getMatKhau() != null && !updatedUser.getMatKhau().isEmpty()) {
            user.setMatKhau(passwordEncoder.encode(updatedUser.getMatKhau()));
        }

        // Lưu lại người dùng đã cập nhật
        return userRepository.save(user);
    }






    // xoá người dùng qua id
    @Transactional
    public void deleteUserId(Integer idKh){
        userRepository.deleteByIdKh(idKh);
    }


    public void processForgotPassword(String email) throws MessagingException {
        // Làm sạch email
        email = email.trim().replaceAll("^,+|,+$", "").toLowerCase();
        System.out.println("Email nhận được sau khi làm sạch: " + email);

        // Tìm user bằng email
        String finalEmail = email;
        User user = userRepository.findByMail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng với email: " + finalEmail));

        // Tạo token đặt lại mật khẩu
        String resetToken = UUID.randomUUID().toString();
        user.setResetToken(resetToken);
        userRepository.save(user);

        // Tạo liên kết đặt lại mật khẩu
        String resetLink = "http://localhost:4200/reset-password?token=" + resetToken;

        // Cấu hình nội dung email
        String subject = "Yêu cầu đặt lại mật khẩu";
        String content = "Xin chào " + user.getTenKh() + ",\n\n" +
                "Bạn đã yêu cầu đặt lại mật khẩu. Vui lòng nhấp vào liên kết dưới đây để đặt lại mật khẩu:\n" +
                resetLink + "\n\n" +
                "Nếu bạn không yêu cầu, vui lòng bỏ qua email này.\n\n" +
                "Trân trọng,\nĐội ngũ hỗ trợ";

        // Gửi email
        emailService.sendEmail(user.getMail(), subject, content, null);
    }

    // Đặt lại mật khẩu bằng token
    public void resetPassword(String token, String newPassword) {
        // Tìm user bằng token
        User user = userRepository.findByResetToken(token)
                .orElseThrow(() -> new RuntimeException("Token không hợp lệ hoặc đã hết hạn"));

        // Đặt lại mật khẩu mới
        user.setMatKhau(passwordEncoder.encode(newPassword));
        user.setResetToken(null); // Xóa token sau khi sử dụng
        log.info("Token nhận được: {}", token);

        userRepository.save(user);
    }




}


