package ru.gavrilov.libraryreact.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gavrilov.libraryreact.dto.BookDto;
import ru.gavrilov.libraryreact.exceptions.BookNotFoundException;
import ru.gavrilov.libraryreact.mappers.BookMapper;
import ru.gavrilov.libraryreact.model.Book;
import ru.gavrilov.libraryreact.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    @Autowired
    public BookController(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @GetMapping("/books")
    public ResponseEntity<List<BookDto>> getAllBooks() {
        return ResponseEntity.ok(bookMapper.toBooksDto(bookService.findAll()));
    }

    @GetMapping("/books/attributes")
    public ResponseEntity<BookDto> getAttributesBook() {
        return ResponseEntity.ok(new BookDto());
    }

    @PostMapping("/books/add")
    public ResponseEntity<BookDto> addBook(@RequestBody BookDto bookDto) {
        Book newBook = bookService.addBook(bookMapper.toBook(bookDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(bookMapper.toBookDto(newBook));
    }

    @GetMapping("/books/{isbn}")
    public ResponseEntity<BookDto> getBook(@PathVariable("isbn") String isbn) {
        Book foundBook = bookService.findByIsbn(isbn).orElseThrow(BookNotFoundException::new);
        return ResponseEntity.ok(bookMapper.toBookDto(foundBook));
    }

    @PutMapping("/books/update")
    public ResponseEntity<BookDto> updateBook(@RequestBody BookDto bookDto) {
        Book updatedBook = bookService.update(bookDto.getId(), bookMapper.toBook(bookDto));
        return ResponseEntity.accepted().body(bookMapper.toBookDto(updatedBook));
    }

    @DeleteMapping("/books/delete/")
    public ResponseEntity deleteBook(@RequestBody String isbn) {
        bookService.delete(bookService.findByIsbn(isbn).orElseThrow(BookNotFoundException::new));
        return ResponseEntity.accepted().build();
    }
}
