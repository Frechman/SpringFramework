package ru.gavrilov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gavrilov.domain.Book;
import ru.gavrilov.domain.Comment;
import ru.gavrilov.repository.CommentRepository;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public void createComment(String content, Book book) {
        commentRepository.createComment(new Comment(content, book));
    }

    @Override
    public List<Comment> findAllCommentsByBook(Book book) {
        return commentRepository.findAllByBook(book);
    }
}
