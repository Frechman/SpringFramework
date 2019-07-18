package ru.gavrilov.repository;

import org.springframework.stereotype.Repository;
import ru.gavrilov.domain.Book;
import ru.gavrilov.domain.Comment;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository {

    List<Comment> findAll();

    List<Comment> findAllByBook(Book book);

    void createComment(Comment comment);

    Optional<Comment> findByUuid(String uuid);

    Optional<Comment> findByContentAndBook(String content, String isbnBook);

    boolean removeComment(String uuid);

    boolean removeComment(Comment comment);
}
