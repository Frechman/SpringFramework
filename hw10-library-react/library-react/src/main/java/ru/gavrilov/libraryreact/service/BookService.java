package ru.gavrilov.libraryreact.service;

import ru.gavrilov.libraryreact.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<Book> findAll();

    Optional<Book> findByIsbn(String isbn);

    Optional<Book> findByTitle(String title);

    Book addBook(Book book);

    Book save(Book book);

    void delete(Book book);

    Book update(String isbn, Book book);

    List<Book> findAllByGenre(String genre);

    List<Book> findAllByAuthor(String authorLastName);

    long count();
}