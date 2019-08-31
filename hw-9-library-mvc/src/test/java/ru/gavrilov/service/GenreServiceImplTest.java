package ru.gavrilov.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.gavrilov.exceptions.GenreNotFoundException;
import ru.gavrilov.model.Genre;
import ru.gavrilov.repository.GenreRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@SpringBootTest
@DisplayName("Методы сервиса работы с жанрами должны ")
class GenreServiceImplTest {

    @Autowired
    private GenreService genreService;
    @MockBean
    private GenreRepository genreRepository;
    private List<Genre> genres;
    private Genre genre;
    private Genre genre2;

    @BeforeEach
    void setUp() {
        this.genre = new Genre("Роман", "Novel");
        this.genre2 = new Genre("Легкое чтиво", "Pulp fiction");
        this.genres = Arrays.asList(genre, genre2);
    }

    @Test
    @DisplayName("найти все жанры.")
    void shouldFindAllGenres() {
        when(genreRepository.findAll()).thenReturn(genres);

        assertThat(genreService.findAll()).isEqualTo(genres);
    }

    @Test
    @DisplayName("найти жанр по id.")
    void shouldFindGenreById() {
        when(genreRepository.findById(1L)).thenReturn(Optional.of(genre));

        assertThat(genreService.findById(1L)).contains(genre);
    }

    @Test
    @DisplayName("выбросить исключение при поиске жанр по id, если id = null.")
    void shouldThrowExceptionIfGenreIdIsNull() {
        assertThatThrownBy(() -> genreService.findById(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Id must not be null!");
    }

    @Test
    @DisplayName("выбросить исключение при сохранении/удалении/обновлении жанр, если genre = null.")
    void shouldThrowExceptionIfGenreIsNull() {
        assertThatThrownBy(() -> genreService.save(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Genre must not be null!");

        assertThatThrownBy(() -> genreService.delete(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Genre must not be null!");

        assertThatThrownBy(() -> genreService.update(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Genre must not be null!");
    }

    @Test
    @DisplayName("сохранять жанр.")
    void shouldSaveGenre() {
        genreService.save(genre);
        verify(genreRepository, times(1)).save(genre);
    }

    @Test
    @DisplayName("удалять жанр.")
    void delete() {
        genreService.delete(genre);
        verify(genreRepository, times(1)).delete(genre);
    }

    @Test
    @DisplayName("обновлять жанр.")
    void update() {
        when(genreRepository.findById(genre.getId())).thenReturn(Optional.of(genre));
        when(genreRepository.save(genre)).thenReturn(genre);

        genreService.update(genre);
        verify(genreRepository, times(1)).save(genre);
    }

    @Test
    @DisplayName("выбросить исключение, если автор не найден.")
    void shouldThrowException() {
        when(genreRepository.findById(genre.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> genreService.update(genre))
                .isInstanceOf(GenreNotFoundException.class)
                .hasMessage("Genre not found!");
    }
}