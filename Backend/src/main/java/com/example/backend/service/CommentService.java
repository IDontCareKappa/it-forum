package com.example.backend.service;

import com.example.backend.dto.CommentUtils;
import com.example.backend.dto.UserReview;
import com.example.backend.model.Comment;

import java.util.List;

public interface CommentService {

    Comment addComment(CommentUtils comment);

    List<Comment> getComments();

    void deleteComment(Long id);

    Comment editComment(Comment comment);

    Comment addThumbUp(UserReview userReview);

    Comment addThumbDown(UserReview userReview);

}
