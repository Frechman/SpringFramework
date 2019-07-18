package ru.gavrilov.repository;

import ru.gavrilov.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {

    List<Genre> findAll();

    Optional<Genre> findById(Long id);

    Optional<Genre> findByName(String name);

    void save(Genre genre);

    void delete(Genre genre);

    Genre update(Genre genre);
}