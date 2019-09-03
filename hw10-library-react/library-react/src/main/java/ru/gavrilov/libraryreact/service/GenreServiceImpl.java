package ru.gavrilov.libraryreact.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gavrilov.libraryreact.exceptions.GenreNotFoundException;
import ru.gavrilov.libraryreact.model.Genre;
import ru.gavrilov.libraryreact.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
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
    public Optional<Genre> findById(Long id) {
        if (id != null) {
            return genreRepository.findById(id);
        }
        throw new IllegalArgumentException("Id must not be null!");
    }

    @Override
    public void save(Genre genre) {
        if (genre != null) {
            genreRepository.save(genre);
            return;
        }
        throw new IllegalArgumentException("Genre must not be null!");
    }

    @Override
    public void delete(Genre genre) {
        if (genre != null) {
            genreRepository.delete(genre);
            return;
        }
        throw new IllegalArgumentException("Genre must not be null!");
    }

    @Override
    public void update(Genre genre) {
        if (genre == null) {
            throw new IllegalArgumentException("Genre must not be null!");
        }
        Genre foundGenre = genreRepository.findById(genre.getId()).orElseThrow(GenreNotFoundException::new);
        genreRepository.save(foundGenre);
    }
}