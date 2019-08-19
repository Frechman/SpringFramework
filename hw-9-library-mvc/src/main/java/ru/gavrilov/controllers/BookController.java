package ru.gavrilov.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gavrilov.exceptions.BookNotFoundException;
import ru.gavrilov.model.Book;
import ru.gavrilov.service.BookService;

import java.util.Optional;

@Controller
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public String listBook(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "listBook";
    }

    @GetMapping("/books/add")
    public String addBook(Model model) {
        model.addAttribute("book", new Book());
        return "addBook";
    }

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
        Book foundBook = bookService.findByIsbn(isbn).orElseThrow(BookNotFoundException::new);
        //// TODO: 20.08.2019 move to bookService
        foundBook.setTitle(book.getTitle());
        foundBook.setAuthor(book.getAuthor());
        foundBook.setGenre(book.getGenre());
        foundBook.setPublishYear(book.getPublishYear());
        bookService.save(foundBook);
        return "redirect:/books/";
    }

    @DeleteMapping("/books/{isbn}")
    public String getBook(@PathVariable("isbn") String isbn) {
        bookService.delete(bookService.findByIsbn(isbn).orElseThrow(BookNotFoundException::new));
        return "redirect:/books/";
    }
}
