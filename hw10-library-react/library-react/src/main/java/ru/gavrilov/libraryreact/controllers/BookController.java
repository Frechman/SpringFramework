package ru.gavrilov.libraryreact.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gavrilov.libraryreact.dto.BookDto;
import ru.gavrilov.libraryreact.mappers.BookMapper;
import ru.gavrilov.libraryreact.service.AuthorService;
import ru.gavrilov.libraryreact.service.BookService;
import ru.gavrilov.libraryreact.service.GenreService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
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
    public List<BookDto> getAllBooks() {
        return bookService.findAll().stream()
                .map(BookMapper.INSTANCE::bookToBookDto).collect(Collectors.toList());
    }

//    @GetMapping("/books/add")
//    public String addBook(Model model) {
//        model.addAttribute("book", new Book());
//        model.addAttribute("authorList", authorService.findAll());
//        model.addAttribute("genreList", genreService.findAll());
//        return "addBook";
//    }
//
//    @PostMapping("/books/add")
//    public String addBook(@ModelAttribute("book") Book book) {
//        bookService.addBook(book);
//        return "redirect:/books/";
//    }
//
//    @GetMapping("/books/{isbn}")
//    public String getBook(@PathVariable("isbn") String isbn, Model model) {
//        Optional<Book> foundBook = bookService.findByIsbn(isbn);
//        model.addAttribute("book", foundBook.orElseThrow(BookNotFoundException::new));
//        return "editBook";
//    }
//
//    @PostMapping("/books/edit")
//    public String editBook(@ModelAttribute("book") Book book) {
//        String isbn = book.getIsbn();
//        bookService.update(isbn, book);
//        return "redirect:/books/";
//    }
//
//    @PostMapping("/books/delete")
//    public String deleteBook(@RequestParam("isbn") String isbn) {
//        bookService.delete(bookService.findByIsbn(isbn).orElseThrow(BookNotFoundException::new));
//        return "redirect:/books/";
//    }
}
