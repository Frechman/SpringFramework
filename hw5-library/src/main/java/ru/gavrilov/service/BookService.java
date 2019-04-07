package ru.gavrilov.service;

import ru.gavrilov.model.Book;

import java.util.List;

public interface BookService {

    List<Book> findAll();

    Book findByIsbn(String isbn);

    void insert(Book book);

    void deleteByIsbn(String isbn);

    List<Book> findAllByGenre(String genreName);

    List<Book> findAllByAuthor(String authorLastName);

    int count();
}
