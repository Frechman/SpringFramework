package ru.gavrilov.service;

import ru.gavrilov.domain.Genre;

import java.util.List;
import java.util.Optional;


public interface GenreService {

    List<Genre> findAll();

    Optional<Genre> findById(Long id);

    void save(Genre genre);

    void delete(Genre genre);

    void update(Genre genre);
}
