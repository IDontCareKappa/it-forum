package com.example.backend.service;

import com.example.backend.dto.CommentUtils;
import com.example.backend.dto.UserReview;
import com.example.backend.exception.*;
import com.example.backend.model.Comment;
import com.example.backend.model.Post;
import com.example.backend.model.User;
import com.example.backend.repo.CommentRepo;
import com.example.backend.repo.PostRepo;
import com.example.backend.repo.UserRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepo commentRepo;
    private final PostRepo postRepo;
    private final UserRepo userRepo;

    @Override
    public Comment addComment(CommentUtils comment) {
        Post post = postRepo.findById(comment.getPostId())
                .orElseThrow(() -> new PostException(PostError.POST_NOT_FOUND));
        Comment newComment = new Comment(
                null,
                comment.getUsername(),
                comment.getContent(),
                LocalDateTime.now(),
                post,
                0,
                0,
                0.0,
                false,
                new ArrayList<>()
        );

        validateComment(newComment);
        return commentRepo.save(newComment);
    }

    @Override
    public List<Comment> getComments() {
        return commentRepo.getSortedComments();
    }

    @Override
    public void deleteComment(Long id) {
        log.info("Deleting comment with id: {}", id);
        Comment comment = commentRepo.findById(id)
                .orElseThrow(() -> new CommentException(CommentError.COMMENT_NOT_FOUND));
        Post post = comment.getPost();
        post.getComments().remove(comment);
        postRepo.save(post);
        commentRepo.delete(comment);
    }

    @Override
    public Comment editComment(Comment comment) {
        validateComment(comment);
        return commentRepo.save(comment);
    }

    @Override
    public Comment addThumbUp(UserReview userReview) {
        Comment comment = commentRepo.findById(userReview.getCommentId())
                .orElseThrow(() -> new CommentException(CommentError.COMMENT_NOT_FOUND));
        User user = userRepo.findByUsername(userReview.getUsername())
                .orElseThrow(() -> new UserException(UserError.USER_NOT_FOUND));
        if (comment.getUsersThatReviewed().contains(user)){
            throw new CommentException(CommentError.COMMENT_ALREADY_REVIEWED);
        }
        comment.getUsersThatReviewed().add(user);
        comment.setThumbUpCount(
                comment.getThumbUpCount() + 1
        );
        comment.setRate(
                comment.getThumbUpCount() - comment.getThumbDownCount()
        );
        return commentRepo.save(comment);
    }

    @Override
    public Comment addThumbDown(UserReview userReview) {
        Comment comment = commentRepo.findById(userReview.getCommentId())
                .orElseThrow(() -> new CommentException(CommentError.COMMENT_NOT_FOUND));
        User user = userRepo.findByUsername(userReview.getUsername())
                .orElseThrow(() -> new UserException(UserError.USER_NOT_FOUND));
        if (comment.getUsersThatReviewed().contains(user)){
            throw new CommentException(CommentError.COMMENT_ALREADY_REVIEWED);
        }
        comment.getUsersThatReviewed().add(user);
        comment.setThumbDownCount(
                comment.getThumbDownCount() + 1
        );
        comment.setRate(
                comment.getThumbUpCount() - comment.getThumbDownCount()
        );
        return commentRepo.save(comment);
    }

    @Override
    public Comment markAsCorrect(Long id) {
        Comment comment = commentRepo.findById(id)
                .orElseThrow(() -> new CommentException(CommentError.COMMENT_NOT_FOUND));

        Set<Comment> postComments = comment.getPost().getComments();

        postComments.forEach(comment1 -> {
            if (comment1.isCorrect()){
                comment1.setCorrect(false);
            }
        });

        commentRepo.saveAll(postComments);

        comment.setCorrect(true);
        return commentRepo.save(comment);
    }

    private void validateComment(Comment comment) {
        if (ObjectUtils.isEmpty(comment.getContent())){
            throw new CommentException(CommentError.COMMENT_CONTENT_EMPTY);
        } else if (ObjectUtils.isEmpty(comment.getUsername())){
            throw new CommentException(CommentError.COMMENT_AUTHOR_EMPTY);
        }
    }
}
