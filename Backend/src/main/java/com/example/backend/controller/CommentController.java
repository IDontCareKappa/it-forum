package com.example.backend.controller;

import com.example.backend.dto.CommentUtils;
import com.example.backend.dto.UserReview;
import com.example.backend.model.Comment;
import com.example.backend.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/comments")
    public List<Comment> getComments(){
        log.info("Fetching all comments");
        return commentService.getComments();
    }

    @PostMapping("/comment")
    public ResponseEntity<Comment> addComment(@RequestBody CommentUtils comment){
        log.info("Adding comment with title: {}", comment.getContent());
        return ResponseEntity.ok()
                .body(commentService.addComment(comment));
    }

    @DeleteMapping("comment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id){
        log.info("Delete comment with id: {}", id);
        commentService.deleteComment(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("comment/plus")
    public ResponseEntity<Comment> addThumbUp(@RequestBody UserReview userReview){
        log.info("Thumb up for comment with id: {} from user: {}", userReview.getCommentId(), userReview.getUsername());
        return ResponseEntity.ok().body(commentService.addThumbUp(userReview));
    }

    @PostMapping("comment/minus")
    public ResponseEntity<Comment> addThumbDown(@RequestBody UserReview userReview){
        log.info("Thumb down for comment with id: {} from user: {}", userReview.getCommentId(), userReview.getUsername());
        return ResponseEntity.ok().body(commentService.addThumbDown(userReview));
    }



}
