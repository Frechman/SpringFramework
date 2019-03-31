package ru.gavrilov.dao;

import ru.gavrilov.model.Book;

import java.util.List;

public interface BookRepository {

    List<Book> findAll();

    Book findByIsbn(String isbn);

    void insert(Book book);

    void deleteByIsbn(String isbn);

    List<Book> findAllByGenre(String genreName);

    List<Book> findAllByAuthor(String authorLastName);

    int count();
}
