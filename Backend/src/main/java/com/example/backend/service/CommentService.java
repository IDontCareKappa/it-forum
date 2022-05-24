package com.example.backend.service;

import com.example.backend.model.Comment;

import java.util.List;

public interface CommentService {

    Comment addComment(Comment comment);

    List<Comment> getComments();

    void deleteComment(Long id);

    Comment editComment(Comment comment);

}
