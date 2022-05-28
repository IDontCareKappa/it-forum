package com.example.backend.repo;

import com.example.backend.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Long> {

    Post findByTitle(String title);
    List<Post> findAllByTag(String tag);
    Integer countPostByAuthor(String author);
    List<Post> getAllByAuthor(String author);

}
