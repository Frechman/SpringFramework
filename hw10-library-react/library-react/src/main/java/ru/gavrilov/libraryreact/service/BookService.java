package ru.gavrilov.libraryreact.service;

import ru.gavrilov.libraryreact.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<Book> findAll();

    Optional<Book> findByIsbn(String isbn);

    Optional<Book> findByTitle(String title);

    Book save(Book book);

    void delete(Book book);

    Book update(Long id, Book book);

    List<Book> findAllByGenre(String genre);

    List<Book> findAllByAuthor(String authorLastName);

    long count();
}