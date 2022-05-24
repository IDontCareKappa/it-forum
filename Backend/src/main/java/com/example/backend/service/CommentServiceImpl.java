package com.example.backend.service;

import com.example.backend.dto.UserReview;
import com.example.backend.exception.CommentError;
import com.example.backend.exception.CommentException;
import com.example.backend.exception.UserError;
import com.example.backend.exception.UserException;
import com.example.backend.model.Comment;
import com.example.backend.model.Post;
import com.example.backend.model.User;
import com.example.backend.repo.CommentRepo;
import com.example.backend.repo.PostRepo;
import com.example.backend.repo.UserRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepo commentRepo;
    private final PostRepo postRepo;
    private final UserRepo userRepo;

    @Override
    public Comment addComment(Comment comment) {
        validateComment(comment);
        return commentRepo.save(comment);
    }

    @Override
    public List<Comment> getComments() {
        return commentRepo.findAll();
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

    private void validateComment(Comment comment) {
        if (ObjectUtils.isEmpty(comment.getContent())){
            throw new CommentException(CommentError.COMMENT_CONTENT_EMPTY);
        } else if (ObjectUtils.isEmpty(comment.getUsername())){
            throw new CommentException(CommentError.COMMENT_AUTHOR_EMPTY);
        }
    }
}
