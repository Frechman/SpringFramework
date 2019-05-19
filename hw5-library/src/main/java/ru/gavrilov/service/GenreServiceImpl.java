package ru.gavrilov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gavrilov.dao.GenreRepository;
import ru.gavrilov.model.Genre;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    @Override
    public Genre findById(Long id) {
        if (id != null) {
            return genreRepository.findById(id);
        }
        throw new IllegalArgumentException("Id must not be null!");
    }

    @Override
    public void insert(Genre genre) {
        if (genre != null) {
            genreRepository.insert(genre);
            return;
        }
        throw new IllegalArgumentException("Genre must not be null!");
    }

    @Override
    public void deleteById(Long id) {
        if (id != null) {
            genreRepository.deleteById(id);
            return;
        }
        throw new IllegalArgumentException("Id must not be null!");
    }

    @Override
    public void update(Long id, Genre genre) {
        Genre updGenre = id == null ? null : genreRepository.findById(id);
        if (updGenre != null) {
            genreRepository.update(id, genre);
        } else {
            genreRepository.insert(genre);
        }
    }
}
