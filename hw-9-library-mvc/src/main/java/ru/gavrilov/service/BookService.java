package ru.gavrilov.service;

import ru.gavrilov.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<Book> findAll();

    Optional<Book> findByIsbn(String isbn);

    Optional<Book> findByTitle(String title);

    void addBook(Book book);

    void save(Book book);

    void delete(Book book);

    void update(String isbn, Book book);

    List<Book> findAllByGenre(String genre);

    List<Book> findAllByAuthor(String authorLastName);

    long count();
}