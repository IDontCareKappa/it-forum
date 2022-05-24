package com.example.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(value = UserException.class)
    public ResponseEntity<ErrorInfo> handleException(UserException e) {
        if (e.getUserError() == UserError.USER_EMAIL_EMPTY ||
                e.getUserError() == UserError.USER_LOGIN_EMPTY){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorInfo(e.getUserError().getMessage()));
        } else if (e.getUserError() == UserError.USER_LOGIN_NOT_AVAILABLE){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorInfo(e.getUserError().getMessage()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorInfo(e.getUserError().getMessage()));
    }

}