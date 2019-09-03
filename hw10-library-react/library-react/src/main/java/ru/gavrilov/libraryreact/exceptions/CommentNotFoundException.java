package ru.gavrilov.libraryreact.exceptions;

public class CommentNotFoundException extends RuntimeException {

    public CommentNotFoundException() {
        super("Comment not found!");
    }

    public CommentNotFoundException(String message) {
        super(message);
    }
}