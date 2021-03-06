package ru.gavrilov.exceptions;

public class AuthorNotFoundException extends RuntimeException {

    public AuthorNotFoundException() {
        super("Author not found!");
    }

    public AuthorNotFoundException(String message) {
        super(message);
    }
}
