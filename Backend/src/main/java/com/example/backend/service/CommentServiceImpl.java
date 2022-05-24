package com.example.backend.service;

import com.example.backend.model.Comment;
import com.example.backend.repo.CommentRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepo commentRepo;

    @Override
    public Comment addComment(Comment comment) {
        return commentRepo.save(comment);
    }

    @Override
    public List<Comment> getComments() {
        return commentRepo.findAll();
    }

    @Override
    public void deleteComment(Long id) {
        commentRepo.findById(id);
    }

    @Override
    public Comment editComment(Comment comment) {
        return commentRepo.save(comment);
    }
}
