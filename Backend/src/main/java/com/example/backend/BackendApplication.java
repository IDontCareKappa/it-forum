package com.example.backend;

import com.example.backend.model.Comment;
import com.example.backend.model.Post;
import com.example.backend.model.User;
import com.example.backend.model.Role;
import com.example.backend.repo.CommentRepo;
import com.example.backend.service.PostService;
import com.example.backend.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

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
    CommandLineRunner run(UserService userService, PostService postService, CommentRepo commentRepo){
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

            Post post1 = new Post (
                    null,
                    "Testowy post",
                    "To jest treść testowego posta!",
                    "tomek",
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    new HashSet<>()
            );

            postService.savePost(post1);

            Comment c1 = new Comment(
                    null,
                    "tomek",
                    "DUPADUAPDUAPDUAPDUAPDUAPDUA",
                    LocalDateTime.now(),
                    post1,
                    0,
                    0,
                    0.0,
                    new HashSet<>()
            );

            Comment c12 = new Comment(
                    null,
                    "admin",
                    "Komentarz admina dla postu o id 1",
                    LocalDateTime.now(),
                    post1,
                    0,
                    0,
                    0.0,
                    new HashSet<>()
            );

            commentRepo.save(c1);
            commentRepo.save(c12);

            post1.getComments().add(c1);
            post1.getComments().add(c12);
            postService.savePost(post1);

            Post post2 = new Post (
                    null,
                    "Testowy post",
                    "To jest treść testowego posta!",
                    "admin",
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    new HashSet<>()
            );
            postService.savePost(post2);

            Comment c2 = new Comment(
                    null,
                    "admin",
                    "post 2 komentarz",
                    LocalDateTime.now(),
                    post2,
                    0,
                    0,
                    0.0,
                    new HashSet<>()
            );

            commentRepo.save(c2);
            post2.getComments().add(c2);
            postService.savePost(post2);

        };
    }

}
