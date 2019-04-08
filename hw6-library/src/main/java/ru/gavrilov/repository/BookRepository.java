package ru.gavrilov.repository;

import ru.gavrilov.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    List<Book> findAll();

    Optional<Book> findByIsbn(String isbn);

    void save(Book book);

    void delete(Book book);

    List<Book> findAllByGenreName(String genreName);

    List<Book> findAllByLastNameAuthor(String authorLastName);

    List<Book> findAllByContentComment(String content);

    int count();
}