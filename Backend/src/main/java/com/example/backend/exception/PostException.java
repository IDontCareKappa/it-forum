package com.example.backend.exception;

public class PostException extends RuntimeException {

    private final PostError postError;

    public PostException(PostError postError) {
        this.postError = postError;
    }

    public PostError getPostError() {
        return postError;
    }
}
