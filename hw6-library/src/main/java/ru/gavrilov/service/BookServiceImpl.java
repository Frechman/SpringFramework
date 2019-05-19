package ru.gavrilov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gavrilov.domain.Book;
import ru.gavrilov.repository.BookRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    public Optional<Book> findByIsbn(String isbn) {
        if (isbn != null) {
            return bookRepository.findByIsbn(isbn);
        }
        throw new IllegalArgumentException("Isbn must not be null!");
    }

    @Override
    public void save(Book book) {
        if (book != null) {
            bookRepository.save(book);
            return;
        }
        throw new IllegalArgumentException("Book must not be null!");
    }

    @Override
    public void delete(Book book) {
        if (book != null) {
            bookRepository.delete(book);
            return;
        }
        throw new IllegalArgumentException("Book must not be null!");
    }

    @Override
    public List<Book> findAllByGenre(String genreName) {
        if (genreName != null) {
            return bookRepository.findAllByGenreName(genreName);
        }
        return Collections.emptyList();
    }

    @Override
    public List<Book> findAllByAuthor(String authorLastName) {
        if (authorLastName != null) {
            return bookRepository.findAllByLastNameAuthor(authorLastName);
        }
        return Collections.emptyList();
    }

    @Override
    public int count() {
        return bookRepository.count();
    }
}
