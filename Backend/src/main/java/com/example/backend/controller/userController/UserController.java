package com.example.backend.controller.userController;

import com.example.backend.entities.user.User;
import com.example.backend.repository.userRepository.UserRepository;
import com.example.backend.services.userService.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @GetMapping("/info")
    public ResponseEntity<User> getUserInfo(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByMail(email).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return ResponseEntity.ok(user);
    }

    @GetMapping("/all")
    public List<User> getAllUser(User user){
        return userService.getAllUser();
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam("query") String query)
    {
        List<User> users = userService.searchUsers(query);
        return ResponseEntity.ok(users);
    }


    @PutMapping("/update")
    public ResponseEntity<User> updateUserDetails(@RequestBody User user) {
        User updatedUser = userService.updateUserDetails(user);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/delete/{idKh}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Integer idKh)
    {
        userService.deleteUserId(idKh);
        return ResponseEntity.noContent().build();
    }



    @GetMapping("/{idKh}")
    public ResponseEntity<User> getUserById(@PathVariable Integer idKh) {
        try {
            User user = userService.findUserByIdKh(idKh);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }


    @PutMapping("/{idKh}")
    public ResponseEntity<User> updateUser(@PathVariable Integer idKh, @RequestBody User updatedUser) {
        User updatedUserInfo = userService.updateUserByIdKh(idKh, updatedUser);

        if (updatedUserInfo != null) {
            return ResponseEntity.ok(updatedUserInfo);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> payload) {
        String token = payload.get("token");
        String newPassword = payload.get("newPassword");

        System.out.println("Token từ client: " + token);
        Optional<User> userOpt = userRepository.findByResetToken(token);

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Token không hợp lệ hoặc đã hết hạn.");
        }

        User user = userOpt.get();
        user.setMatKhau(passwordEncoder.encode(newPassword));
        user.setResetToken(null); // Xóa token sau khi dùng
        userRepository.save(user);

        return ResponseEntity.ok(Map.of("message", "Mật khẩu đã được đặt lại thành công."));

    }




}


