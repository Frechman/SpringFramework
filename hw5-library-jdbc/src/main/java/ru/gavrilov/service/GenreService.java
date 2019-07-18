package ru.gavrilov.service;

import ru.gavrilov.model.Genre;

import java.util.List;

public interface GenreService {

    List<Genre> findAll();

    Genre findById(Long id);

    void insert(Genre genre);

    void deleteById(Long id);

    void update(Long id, Genre genre);
}
