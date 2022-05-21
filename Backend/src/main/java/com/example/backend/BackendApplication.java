package com.example.backend;

import com.example.backend.model.User;
import com.example.backend.model.Role;
import com.example.backend.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner run(UserService userService){
        return args -> {
            userService.saveRole(
                    new Role(null, "ROLE_USER")
            );
            userService.saveRole(
                    new Role(null, "ROLE_ADMIN")
            );

            userService.saveUser(
                    new User(null, "tomek", "12345678", "tomek@gmail.com", new ArrayList<>())
            );
            userService.saveUser(
                    new User(null, "admin", "admin123", "admin@gmail.com", new ArrayList<>())
            );

            userService.addRoleToUser("tomek", "ROLE_USER");
            userService.addRoleToUser("admin", "ROLE_USER");
            userService.addRoleToUser("admin", "ROLE_ADMIN");
        };
    }

}
