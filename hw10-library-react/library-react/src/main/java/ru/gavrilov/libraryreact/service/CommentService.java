package ru.gavrilov.libraryreact.service;

import ru.gavrilov.libraryreact.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    Comment addComment(String isbnBook, String content);

    boolean removeComment(String isbn, String content);

    List<Comment> findAll();

    List<Comment> findAllCommentsByBook(String isbnBook);

    Optional<Comment> findByContentAndBook(String isbnBook, String content);
}