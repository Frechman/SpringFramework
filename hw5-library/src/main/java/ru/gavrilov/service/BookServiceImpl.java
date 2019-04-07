package ru.gavrilov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gavrilov.dao.BookRepository;
import ru.gavrilov.model.Book;

import java.util.Collections;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findByIsbn(String isbn) {
        if (isbn != null) {
            return bookRepository.findByIsbn(isbn);
        }
        throw new IllegalArgumentException("Isbn is not null!");
    }

    @Override
    public void insert(Book book) {
        if (book != null) {
            bookRepository.insert(book);
            return;
        }
        throw new IllegalArgumentException("Book is not null!");
    }

    @Override
    public void deleteByIsbn(String isbn) {
        if (isbn != null) {
            bookRepository.deleteByIsbn(isbn);
            return;
        }
        throw new IllegalArgumentException("Isbn is not null!");
    }

    @Override
    public List<Book> findAllByGenre(String genreName) {
        if (genreName != null) {
            return bookRepository.findAllByGenre(genreName);
        }
        return Collections.emptyList();
    }

    @Override
    public List<Book> findAllByAuthor(String authorLastName) {
        if (authorLastName != null) {
            return bookRepository.findAllByAuthor(authorLastName);
        }
        return Collections.emptyList();
    }

    @Override
    public int count() {
        return bookRepository.count();
    }

}
