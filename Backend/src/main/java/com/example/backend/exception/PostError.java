package com.example.backend.exception;

public enum PostError {

    POST_NOT_FOUND("Post nie istnieje"),
    POST_CONTENT_EMPTY("Treść posta nie może być pusty"),
    POST_AUTHOR_EMPTY("Post musi posiadać autora"),
    POST_TITLE_EMPTY("Tytuł posta nie może być pusty"),
    POST_TITLE_TO_LONG("Tytuł posta nie może być dłuższy niż 100 znaków"),
    ;

    private final String message;

    PostError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
