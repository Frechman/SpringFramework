package ru.gavrilov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gavrilov.model.Book;
import ru.gavrilov.model.Comment;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {

    @Query("select c from Comment c join fetch c.book")
    List<Comment> findAll();

    List<Comment> findAllByBook(Book book);

    Optional<Comment> findByContentAndBook(String content, Book book);
}
