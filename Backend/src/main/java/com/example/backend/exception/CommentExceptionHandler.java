package com.example.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommentExceptionHandler {

    @ExceptionHandler(value = CommentException.class)
    public ResponseEntity<ErrorInfo> handleException(CommentException e) {
        if (e.getCommentError() == CommentError.COMMENT_CONTENT_EMPTY ||
                e. getCommentError() == CommentError.COMMENT_AUTHOR_EMPTY){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorInfo(e.getCommentError().getMessage()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorInfo(e.getCommentError().getMessage()));
    }

}
