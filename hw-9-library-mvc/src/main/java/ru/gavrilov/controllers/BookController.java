package ru.gavrilov.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gavrilov.exceptions.BookNotFoundException;
import ru.gavrilov.model.Book;
import ru.gavrilov.service.AuthorService;
import ru.gavrilov.service.BookService;
import ru.gavrilov.service.GenreService;

import java.util.Optional;

@Controller
public class BookController {

    private final BookService bookService;
    private final GenreService genreService;
    private final AuthorService authorService;

    @Autowired
    public BookController(BookService bookService, GenreService genreService, AuthorService authorService) {
        this.bookService = bookService;
        this.genreService = genreService;
        this.authorService = authorService;
    }

    @GetMapping("/books")
    public String listBook(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "listBooks";
    }

    @GetMapping("/books/add")
    public String addBook(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("authorList", authorService.findAll());
        model.addAttribute("genreList", genreService.findAll());
        return "addBook";
    }

    //// TODO: 27.08.2019 fix
    @PostMapping("/books/add")
    public String addBook(@ModelAttribute("book") Book book) {
        bookService.save(book);
        return "redirect:/books/";
    }

    @GetMapping("/books/{isbn}")
    public String getBook(@PathVariable("isbn") String isbn, Model model) {
        Optional<Book> foundBook = bookService.findByIsbn(isbn);
        model.addAttribute("book", foundBook.orElseThrow(BookNotFoundException::new));
        return "showBook";
    }

    @PutMapping("/books/{isbn}/update")
    public String getBook(@PathVariable("isbn") String isbn, @ModelAttribute("book") Book book) {
        bookService.update(isbn, book);
        return "redirect:/books/";
    }

    @PostMapping("/books/{isbn}/delete")
    public String getBook(@PathVariable("isbn") String isbn) {
        bookService.delete(bookService.findByIsbn(isbn).orElseThrow(BookNotFoundException::new));
        return "redirect:/books/";
    }
}
