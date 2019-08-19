package ru.gavrilov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.gavrilov.model.Book;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b JOIN FETCH b.author a JOIN FETCH b.genre g")
    List<Book> findAll();

    @Query("SELECT b FROM Book b JOIN FETCH b.author a JOIN FETCH b.genre g WHERE b.isbn = :isbn")
    Optional<Book> findByIsbn(@Param("isbn") String isbn);

    @Query("SELECT b FROM Book b JOIN FETCH b.author a JOIN FETCH b.genre g WHERE b.title = :title")
    Optional<Book> findByTitle(@Param("title") String title);

    @Query("SELECT b FROM Book b JOIN FETCH b.author a JOIN FETCH b.genre g WHERE a.lastName = :authorLastName")
    List<Book> findAllByAuthorLastName(@Param("authorLastName") String authorLastName);

    @Query("SELECT b FROM Book b JOIN FETCH b.author a JOIN FETCH b.genre g WHERE g.name = :genreName")
    List<Book> findAllByGenreName(@Param("genreName") String genreName);
}
