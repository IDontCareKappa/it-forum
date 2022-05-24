package com.example.backend.exception;

public enum CommentError {

    COMMENT_NOT_FOUND("Komentarz nie istnieje"),
    COMMENT_CONTENT_EMPTY("Treść komentarza nie może być pusta"),
    COMMENT_AUTHOR_EMPTY("Komentarz musi posiadać autora"),
    COMMENT_ALREADY_REVIEWED("Użytkownik już ocenił komentarz");

    private final String message;

    CommentError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
