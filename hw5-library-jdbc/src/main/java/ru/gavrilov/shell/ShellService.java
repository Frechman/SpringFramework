package ru.gavrilov.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.gavrilov.model.Book;
import ru.gavrilov.service.BookService;

import java.util.stream.Collectors;

@ShellComponent
public class ShellService {

    private final BookService bookService;

    @Autowired
    public ShellService(BookService bookService) {
        this.bookService = bookService;
    }

    @ShellMethod(value = "Show all books", key = "all-books")
    public String getAllBooks() {
        return bookService.findAll().stream()
                .map(Book::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @ShellMethod(value = "Show all books by the author's last name", key = "all-books-author")
    public String getAllBooksByAuthorLastName(@ShellOption String authorLastName) {
        return bookService.findAllByAuthor(authorLastName).stream()
                .map(Book::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @ShellMethod(value = "Show all books by genre", key = "all-books-genre-name")
    public String getAllBooksByGenre(@ShellOption String genreName) {
        return bookService.findAllByGenre(genreName).stream()
                .map(Book::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @ShellMethod(value = "Show count all books", key = "count-books")
    public String getCountAllBooks() {
        return String.valueOf(bookService.count());
    }
}
