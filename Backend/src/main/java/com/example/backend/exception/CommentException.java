package com.example.backend.exception;

public class CommentException extends RuntimeException {

    private final CommentError commentError;

    public CommentException(CommentError commentError) {
        this.commentError = commentError;
    }

    public CommentError getCommentError() {
        return commentError;
    }
}
