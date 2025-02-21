package com.example.backend.services.emailService;

import com.example.backend.entities.user.Role;
import com.example.backend.entities.user.User;
import com.example.backend.repository.userRepository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.from}")
    private String emailfrom;

    @Autowired
    private UserRepository userRepository;


    public String sendEmail(String recipients, String subject, String content, MultipartFile[] files) throws MessagingException {
        log.info("Sending ...");


        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,true,"UTF-8");

        mimeMessageHelper.setFrom(emailfrom);
        if (recipients.contains(",")){
            mimeMessageHelper.setTo(InternetAddress.parse(recipients));

        }else {
            mimeMessageHelper.setTo(recipients);
        }
        if (files != null){
            for (MultipartFile file: files){
                mimeMessageHelper.addAttachment(Objects.requireNonNull(file.getOriginalFilename()),file);

            }
        }

        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(content,true);
        mailSender.send(message);
        log.info("Email has been sent successfully, recipient={}", recipients);
        return "sent";
    }


    public String sendEmailToUser(int userId, String subject, String content, MultipartFile[] files) throws MessagingException {
        User user = userRepository.findByIdKh(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        // Thiết lập tiêu đề mặc định nếu không có tiêu đề
        if (subject == null || subject.isEmpty()) {
            subject = "Mail thông báo khách hàng của Nin store !";
        }

        // Thêm dòng chào hỏi vào nội dung email
        String greeting = "Chào " + user.getTenKh() + "!\n\n";
        content = greeting + content;

        return sendEmail(user.getMail(), subject, content, files);
    }


    public String sendEmailToAdmins(String orderCode, String content, MultipartFile[] files) throws MessagingException {
        // Lấy danh sách các user có role là ADMIN
        List<User> admins = getAdmins();
        String recipients = admins.stream()
                .map(User::getMail)
                .collect(Collectors.joining(","));

        String subject = "Đơn hàng đã huỷ + " + orderCode;

        return sendEmail(recipients, subject, content, files);
    }

    private List<User> getAdmins() {
        // Sử dụng phương thức findByRole để lấy danh sách các admin
        return userRepository.findAll().stream()
                .filter(user -> user.getRole() == Role.ADMIN)
                .collect(Collectors.toList());
    }
}
