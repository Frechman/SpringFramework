package ru.gavrilov.libraryreact.exceptions;

public class GenreNotFoundException extends RuntimeException {

    public GenreNotFoundException() {
        super("Genre not found!");
    }

    public GenreNotFoundException(String message) {
        super(message);
    }
}