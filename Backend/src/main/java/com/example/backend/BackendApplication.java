package com.example.backend;

import com.example.backend.entities.user.Role;
import com.example.backend.entities.user.User;
import com.example.backend.repository.userRepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class BackendApplication  implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	public void run(String... args){
		User adminAccount = userRepository.findByRole(Role.ADMIN);
		if (null == adminAccount){
			User user = new User();
			user.setMail("lenhathao280302@gmail.com");
			user.setTenKh("Hào Lê Nhật");
			user.setRole(Role.ADMIN);
			user.setMatKhau(new BCryptPasswordEncoder().encode("admin"));
			userRepository.save(user);
		}
	}
}
