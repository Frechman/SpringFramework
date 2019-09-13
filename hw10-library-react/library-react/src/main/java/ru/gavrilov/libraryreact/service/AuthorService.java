package ru.gavrilov.libraryreact.service;

import ru.gavrilov.libraryreact.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    List<Author> findAll();

    Optional<Author> findById(Long id);

    void save(Author author);

    void delete(Author author);

    void update(Author author);
}