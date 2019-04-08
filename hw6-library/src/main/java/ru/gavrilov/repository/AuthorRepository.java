package ru.gavrilov.repository;

import ru.gavrilov.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {

    List<Author> findAll();

    Optional<Author> findById(Long id);

    Optional<Author> findByLastName(String lastName);

    void save(Author author);

    void delete(Author author);

    Author update(Author author);
}