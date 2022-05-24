package com.example.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PostExceptionHandler {

    @ExceptionHandler(value = PostException.class)
    public ResponseEntity<ErrorInfo> handleException(PostException e) {
        if (e.getPostError() == PostError.POST_CONTENT_EMPTY ||
                e.getPostError() == PostError.POST_TITLE_EMPTY ||
                e. getPostError() == PostError.POST_AUTHOR_EMPTY){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorInfo(e.getPostError().getMessage()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorInfo(e.getPostError().getMessage()));
    }

}
