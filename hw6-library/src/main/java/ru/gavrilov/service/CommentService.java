package ru.gavrilov.service;

import ru.gavrilov.domain.Book;
import ru.gavrilov.domain.Comment;

import java.util.List;

public interface CommentService {

    void createComment(String content, Book book);

    List<Comment> findAllCommentsByBook(Book book);
}
