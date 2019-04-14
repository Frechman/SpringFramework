package ru.gavrilov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gavrilov.domain.Genre;
import ru.gavrilov.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

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
    public Optional<Genre> findById(Long id) {
        if (id != null) {
            return genreRepository.findById(id);
        }
        throw new IllegalArgumentException("Id is not null!");
    }

    @Override
    public void save(Genre genre) {
        if (genre != null) {
            genreRepository.save(genre);
            return;
        }
        throw new IllegalArgumentException("Genre is not null!");
    }

    @Override
    public void delete(Genre genre) {
        if (genre != null) {
            genreRepository.delete(genre);
            return;
        }
        throw new IllegalArgumentException("Genre is not null!");
    }

    @Override
    public void update(Genre genre) {
        if (genre != null) {
            throw new IllegalArgumentException("Author is not null!");
        }
        Optional<Genre> foundGenre = genreRepository.findById(genre.getId());
        if (foundGenre.isPresent()) {
            genreRepository.update(genre);
        } else {
            genreRepository.save(genre);
        }
    }
}
