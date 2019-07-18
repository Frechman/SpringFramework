package ru.gavrilov.service;

import ru.gavrilov.model.Author;

import java.util.List;

public interface AuthorService {

    List<Author> findAll();

    Author findById(Long id);

    void insert(Author author);

    void deleteById(Long id);

    void update(Long id, Author author);

}
