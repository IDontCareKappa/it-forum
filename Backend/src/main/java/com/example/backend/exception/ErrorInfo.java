package com.example.backend.exception;

public class ErrorInfo {

    private final String message;

    public ErrorInfo(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
