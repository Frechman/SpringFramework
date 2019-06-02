package ru.gavrilov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gavrilov.model.Book;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {

    Optional<Book> findByIsbn(String isbn);

    Optional<Book> findByTitle(String title);

    List<Book> findAllByAuthorLastName(String authorLastName);

    List<Book> findAllByGenreName(String genre);
}
