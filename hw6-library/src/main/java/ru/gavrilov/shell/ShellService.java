package ru.gavrilov.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.gavrilov.domain.Author;
import ru.gavrilov.domain.Book;
import ru.gavrilov.domain.Comment;
import ru.gavrilov.domain.Genre;
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

    @ShellMethod("Add comment to Book.")
    public String createComment(@ShellOption String isbn, @ShellOption String text) {
        Optional<Book> book = bookService.findByIsbn(isbn);
        if (book.isPresent()) {
            commentService.createComment(text, book.get());
            return text;
        }
        return "Book not found.";
    }

    @ShellMethod("Remove comment.")
    public String removeComment(@ShellOption String isbn, @ShellOption String text) {
        String result = "Комментарий не удалён";
        Optional<Comment> comment = commentService.findByContentAndBook(text, isbn);
        if (comment.isPresent()) {
            result = commentService.removeComment(comment.get()) ? "Коментарий удалён" : result;
        }
        return result;
    }
}