package ru.gavrilov.libraryreact.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gavrilov.libraryreact.exceptions.BookNotFoundException;
import ru.gavrilov.libraryreact.exceptions.CommentNotFoundException;
import ru.gavrilov.libraryreact.model.Book;
import ru.gavrilov.libraryreact.model.Comment;
import ru.gavrilov.libraryreact.repository.CommentRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BookService bookService;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, BookService bookService) {
        this.commentRepository = commentRepository;
        this.bookService = bookService;
    }

    @Override
    public Comment addComment(String isbnBook, String content) {
        if (content == null || isbnBook == null) {
            throw new IllegalArgumentException("Content or isbn book must not be null");
        }
        Book book = bookService.findByIsbn(isbnBook).orElseThrow(BookNotFoundException::new);
        return commentRepository.save(new Comment(content, book));
    }

    @Override
    public boolean removeComment(String isbnBook, String content) {
        Comment comment = findByContentAndBook(isbnBook, content).orElseThrow(CommentNotFoundException::new);
        commentRepository.delete(comment);
        return true;
    }

    @Override
    public List<Comment> findAllCommentsByBook(String isbnBook) {
        if (isbnBook == null) {
            throw new IllegalArgumentException("Isbn must not be null");
        }
        Book book = bookService.findByIsbn(isbnBook).orElseThrow(BookNotFoundException::new);
        return commentRepository.findAllByBook(book);
    }

    @Override
    public Optional<Comment> findByContentAndBook(String isbnBook, String content) {
        if (content == null || isbnBook == null) {
            throw new IllegalArgumentException("Content or Isbn must not be null");
        }
        Book book = bookService.findByIsbn(isbnBook).orElseThrow(BookNotFoundException::new);
        return commentRepository.findByContentAndBook(content, book);
    }

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }
}