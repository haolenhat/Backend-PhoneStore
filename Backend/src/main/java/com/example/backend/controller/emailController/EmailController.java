package com.example.backend.controller.emailController;

import com.example.backend.services.emailService.EmailService;
import com.example.backend.services.userService.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/mail")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(
            @RequestParam("recipients") String recipients,
            @RequestParam("subject") String subject,
            @RequestParam("content") String content,
            @RequestParam(value = "files", required = false) MultipartFile[] files) {
        try {
            String response = emailService.sendEmail(recipients, subject, content, files);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error while sending email: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send email: " + e.getMessage());
        }
    }


    private final UserService userService;

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam("email") String email) {
        try {
            // Gọi service để xử lý quên mật khẩu
            userService.processForgotPassword(email);

            // Trả về phản hồi thành công
            java.util.Map<String, String> response = new HashMap<>();
            response.put("message", "Đã gửi email khôi phục mật khẩu. Vui lòng kiểm tra email của bạn.");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            // Trả về lỗi nếu không tìm thấy user
            java.util.Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (MessagingException e) {
            // Trả về lỗi nếu gửi email thất bại
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Không thể gửi email. Vui lòng thử lại sau.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping("/send-to-user/{id}")
    public ResponseEntity<String> sendEmailToUser(
            @PathVariable int id,
            @RequestParam("subject") String subject,
            @RequestParam("content") String content,
            @RequestParam(value = "files", required = false) MultipartFile[] files) {
        try {
            String response = emailService.sendEmailToUser(id, subject, content, files);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error while sending email to user: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send email: " + e.getMessage());
        }
    }

    @PostMapping("/send-to-admins")
    public ResponseEntity<Map<String, String>> sendEmailToAdmins(
            @RequestParam("orderCode") String orderCode,
            @RequestParam("content") String content,
            @RequestParam(value = "files", required = false) MultipartFile[] files) {
        Map<String, String> response = new HashMap<>();
        try {
            String result = emailService.sendEmailToAdmins(orderCode, content, files);
            response.put("message", result);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error while sending email to admins: {}", e.getMessage(), e);
            response.put("error", "Failed to send email: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
