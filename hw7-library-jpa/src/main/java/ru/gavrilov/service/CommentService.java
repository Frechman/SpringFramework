package ru.gavrilov.service;

import ru.gavrilov.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    Comment addComment(String content, String isbnBook);

    boolean removeComment(String isbn, String content);

    List<Comment> findAll();

    List<Comment> findAllCommentsByBook(String isbnBook);

    Optional<Comment> findByContentAndBook(String content, String isbnBook);
}