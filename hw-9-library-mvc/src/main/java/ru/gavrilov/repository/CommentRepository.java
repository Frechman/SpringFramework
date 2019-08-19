package ru.gavrilov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.gavrilov.model.Book;
import ru.gavrilov.model.Comment;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {

    @Query("SELECT c FROM Comment c JOIN FETCH c.book")
    List<Comment> findAll();

    @Query("SELECT c FROM Comment c JOIN FETCH c.book b WHERE b.isbn = :#{book.isbn}")
    List<Comment> findAllByBook(@Param("book") Book book);

    @Query("SELECT c FROM Comment c JOIN FETCH c.book b WHERE c.content LIKE %:content% AND b.isbn = :#{book.isbn}")
    Optional<Comment> findByContentAndBook(@Param("content") String content, @Param("book") Book book);
}
