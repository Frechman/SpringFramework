package ru.gavrilov.service;

import ru.gavrilov.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<Book> findAll();

    Optional<Book> findByIsbn(String isbn);

    void save(Book book);

    void delete(Book book);

    List<Book> findAllByGenre(String genreName);

    List<Book> findAllByAuthor(String authorLastName);

    int count();
}
