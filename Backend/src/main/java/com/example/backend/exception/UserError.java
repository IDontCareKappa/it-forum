package com.example.backend.exception;

public enum UserError {

    USER_NOT_FOUND("Uzytkownik nie istnieje"),
    USER_LOGIN_NOT_AVAILABLE("Podany login jest już zajęty"),
    USER_EMAIL_NOT_AVAILABLE("Podany email jest już zajęty"),
    USER_EMAIL_EMPTY("Email nie może być pusty"),
    USER_LOGIN_EMPTY("Login nie może być pusty");

    private final String message;

    UserError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
