package ru.gavrilov.service;

import ru.gavrilov.domain.Book;
import ru.gavrilov.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    void createComment(String content, Book book);

    boolean removeComment(String uuid);

    boolean removeComment(Comment comment);

    List<Comment> findAll();

    List<Comment> findAllCommentsByBook(Book book);

    Optional<Comment> findByContentAndBook(String content, String isbnBook);
}
