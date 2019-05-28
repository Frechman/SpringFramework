package ru.gavrilov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gavrilov.domain.Book;
import ru.gavrilov.domain.Comment;
import ru.gavrilov.repository.CommentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BookService bookService;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, BookService bookService) {
        this.commentRepository = commentRepository;
        this.bookService = bookService;
    }

    @Override
    public void createComment(String content, Book book) {
        if (content == null || book == null) {
            throw new IllegalArgumentException("Content or Book must not be null");
        }
        commentRepository.createComment(new Comment(content, book));
    }

    @Override
    public boolean removeComment(String uuid) {
        if (uuid == null) {
            throw new IllegalArgumentException("Uuid must not be null!");
        }
        return commentRepository.removeComment(uuid);
    }

    @Override
    public boolean removeComment(Comment comment) {
        if (comment == null) {
            throw new IllegalArgumentException("Comment must not be null!");
        }
        return commentRepository.removeComment(comment.getUuid());
    }

    @Override
    public List<Comment> findAllCommentsByBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book must not be null");
        }
        return commentRepository.findAllByBook(book);
    }

    @Override
    public Optional<Comment> findByContentAndBook(String content, String isbnBook) {
        if (content == null || isbnBook == null) {
            throw new IllegalArgumentException("Content or Isbn must not be null");
        }
        bookService.findByIsbn(isbnBook).orElseThrow(() -> new IllegalArgumentException("Book not found"));
        return commentRepository.findByContentAndBook(content, isbnBook);
    }

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }
}