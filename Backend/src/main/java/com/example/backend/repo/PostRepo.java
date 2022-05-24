package com.example.backend.repo;

import com.example.backend.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepo extends JpaRepository<Post, Long> {

    Post findByTitle(String title);

}
