package ru.gavrilov.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.gavrilov.model.Author;
import ru.gavrilov.model.Book;
import ru.gavrilov.model.Comment;
import ru.gavrilov.model.Genre;
import ru.gavrilov.service.AuthorService;
import ru.gavrilov.service.BookService;
import ru.gavrilov.service.CommentService;
import ru.gavrilov.service.GenreService;

import java.util.Optional;
import java.util.stream.Collectors;

@ShellComponent
public class ShellService {

    private final BookService bookService;
    private final CommentService commentService;
    private final GenreService genreService;
    private final AuthorService authorService;

    @Autowired
    public ShellService(BookService bookService, GenreService genreService,
                        AuthorService authorService, CommentService commentService) {
        this.bookService = bookService;
        this.commentService = commentService;
        this.genreService = genreService;
        this.authorService = authorService;
    }

    @ShellMethod("Print all books.")
    public String getAllBooks() {
        return bookService.findAll().stream()
                .map(Book::toString).collect(Collectors.joining(System.lineSeparator()));
    }

    @ShellMethod("Print all genres.")
    public String getAllGenres() {
        return genreService.findAll().stream()
                .map(Genre::toString).collect(Collectors.joining(System.lineSeparator()));
    }

    @ShellMethod("Print all authors.")
    public String getAllAuthors() {
        return authorService.findAll().stream()
                .map(Author::toString).collect(Collectors.joining(System.lineSeparator()));
    }

    @ShellMethod("Print all comments.")
    public String getAllComments() {
        return commentService.findAll().stream()
                .map(Comment::toString).collect(Collectors.joining(System.lineSeparator()));
    }

    @ShellMethod("Find book by title.")
    public String getBookByTitle(@ShellOption String title) {
        Optional<Book> book = bookService.findByTitle(title);
        return book.map(Book::toString).orElse("The book is not found.");
    }

    @ShellMethod("Find book by author's last name.")
    public String getBooksByAuthor(@ShellOption String lastName) {
        return bookService.findAllByAuthor(lastName).stream()
                .map(Book::toString).collect(Collectors.joining(System.lineSeparator()));
    }

    @ShellMethod("Find book by genre.")
    public String getBooksByGenre(@ShellOption String genre) {
        return bookService.findAllByGenre(genre).stream()
                .map(Book::toString).collect(Collectors.joining(System.lineSeparator()));
    }

    @ShellMethod("Print all comments of the book.")
    public String getCommentsOfBook(@ShellOption String isbn) {
        return commentService.findAllCommentsByBook(isbn).stream()
                .map(Comment::toString).collect(Collectors.joining(System.lineSeparator()));
    }

    @ShellMethod("Add comment to book.")
    public String addComment(@ShellOption String isbn, @ShellOption String text) {
        return commentService.addComment(text, isbn).toString();
    }

    @ShellMethod("Remove comment.")
    public String removeComment(@ShellOption String isbn, @ShellOption String text) {
        return commentService.removeComment(isbn, text) ?
                "Comment has been deleted." : "Comment has NOT been deleted.";
    }
}