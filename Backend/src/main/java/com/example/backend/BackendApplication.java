package com.example.backend;

import com.example.backend.model.Comment;
import com.example.backend.model.Post;
import com.example.backend.model.User;
import com.example.backend.model.Role;
import com.example.backend.repo.CommentRepo;
import com.example.backend.repo.PostRepo;
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
    CommandLineRunner run(UserService userService, PostRepo postRepo, CommentRepo commentRepo){
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
                    "How to Store Http json data into variables and calculate with firestore data Flutter",
                    "How to take http json data and store in variables and in the same page read firestore data and calculate with reference to http json data and display\n" +
                            "\n" +
                            "Example: Json Data : a=1,b=2,c=3 Firestore Data : d=4,e=5,f=6\n" +
                            "\n" +
                            "How to do sum= a*d ? in Flutter",
                    "tomek",
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    "flutter",
                    new HashSet<>()
            );

            postRepo.save(post1);

            Comment c1 = new Comment(
                    null,
                    "tomek",
                    "Consequatur totam voluptatem ipsam voluptate aut eos debitis. Quis commodi autem non repellat. Saepe corrupti non qui nisi qui. Dolores quis placeat dolorem asperiores rerum.",
                    LocalDateTime.now(),
                    post1,
                    0,
                    0,
                    0.0,
                    false,
                    new HashSet<>()
            );

            Comment c12 = new Comment(
                    null,
                    "admin",
                    "Consequatur totam voluptatem ipsam voluptate aut eos debitis. Quis commodi autem non repellat. Saepe corrupti non qui nisi qui. Dolores quis placeat dolorem asperiores rerum.",
                    LocalDateTime.now(),
                    post1,
                    0,
                    0,
                    0.0,
                    true,
                    new HashSet<>()
            );

            commentRepo.save(c1);
            commentRepo.save(c12);

            post1.getComments().add(c1);
            post1.getComments().add(c12);
            postRepo.save(post1);

            Post post2 = new Post (
                    null,
                    "IllegalStateException in conditional observer in Vaadin @RouteScoped component",
                    "We are using Vaadin 23 (23.0.9) with vaadin-cdi (14.0.0) and are having problems with conditional observers in @RouteScoped components (like it is described in the tutorial):\n" +
                            "\n" +
                            "@Route(\"scoped\")\n" +
                            "@RouteScoped\n" +
                            "public class ScopedView extends Div {\n" +
                            "    private void onMessage(\n" +
                            "            @Observes(notifyObserver = IF_EXISTS)\n" +
                            "            MessageEvent message) {\n" +
                            "        setText(message.getText());\n" +
                            "    }\n" +
                            "}\n" +
                            "We are running on Tomcat 9 and use Weld (3.1.9.Final) as our CDI implementation. Our current problem is that we get the following exception when firing an event that is observed in a @RouteScoped component:",
                    "admin",
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    "Vaadin",
                    new HashSet<>()
            );
            postRepo.save(post2);

            Comment c2 = new Comment(
                    null,
                    "admin",
                    "Consequatur totam voluptatem ipsam voluptate aut eos debitis. Quis commodi autem non repellat. Saepe corrupti non qui nisi qui. Dolores quis placeat dolorem asperiores rerum.",
                    LocalDateTime.now(),
                    post2,
                    0,
                    0,
                    0.0,
                    false,
                    new HashSet<>()
            );

            commentRepo.save(c2);
            post2.getComments().add(c2);
            postRepo.save(post2);

        };
    }

}
