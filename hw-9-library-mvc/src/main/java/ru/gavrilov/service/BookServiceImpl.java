package ru.gavrilov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gavrilov.exceptions.BookNotFoundException;
import ru.gavrilov.model.Book;
import ru.gavrilov.repository.BookRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
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
    public Optional<Book> findByTitle(String title) {
        if (title != null) {
            return bookRepository.findByTitle(title);
        }
        throw new IllegalArgumentException("Title must not be null!");
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
    public void update(String isbn, Book book) {
        Book foundBook = findByIsbn(isbn).orElseThrow(BookNotFoundException::new);
        //// TODO: 20.08.2019 refactoring
        foundBook.setTitle(book.getTitle());
        foundBook.setPublishYear(book.getPublishYear());
        save(foundBook);
    }

    @Override
    public List<Book> findAllByGenre(String genre) {
        if (genre != null) {
            return bookRepository.findAllByGenreName(genre);
        }
        return Collections.emptyList();
    }

    @Override
    public List<Book> findAllByAuthor(String authorLastName) {
        if (authorLastName != null) {
            return bookRepository.findAllByAuthorLastName(authorLastName);
        }
        return Collections.emptyList();
    }

    @Override
    public long count() {
        return bookRepository.count();
    }
}