package ru.gavrilov.dao;

import ru.gavrilov.model.Author;

import java.util.List;

public interface AuthorRepository {

    List<Author> findAll();

    Author findById(Long id);

    void insert(Author author);

    void deleteById(Long id);

    void update(Long id, Author author);
}
