package com.example.backend.repo;

import com.example.backend.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Long> {

    @Query(value = "SELECT * FROM comment ORDER BY is_correct DESC ", nativeQuery = true)
    List<Comment> getSortedComments();

}
