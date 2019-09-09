package ru.gavrilov.libraryreact.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.gavrilov.libraryreact.mappers.GenreMapper;
import ru.gavrilov.libraryreact.model.Genre;
import ru.gavrilov.libraryreact.service.GenreService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {GenreController.class, GenreMapper.class})
@DisplayName("Genre controller's methods should ")
class GenreControllerTest {

    private static final String API = "/api/v1";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GenreService genreService;

    @Autowired
    private GenreMapper genreMapper;

    private List<Genre> genreList;
    private Genre genre;
    private Genre genre2;

    @BeforeEach
    void init() {
        this.genre = new Genre("Роман", "Novel");
        this.genre2 = new Genre("Легкое чтиво", "Pulp fiction");
        this.genreList = Arrays.asList(genre, genre2);
    }

    @Test
    @DisplayName("return json array of genre when a GET request was made for '/genres'")
    void getAllGenres() throws Exception {
        when(genreService.findAll()).thenReturn(genreList);

        mockMvc.perform(get(API + "/genres"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(genre.getId())))
                .andExpect(jsonPath("$[0].name", is(genre.getName())))
                .andExpect(jsonPath("$[0].description", is(genre.getDescription())))
                .andExpect(jsonPath("$[1].id", is(genre2.getId())))
                .andExpect(jsonPath("$[1].name", is(genre2.getName())))
                .andExpect(jsonPath("$[1].description", is(genre2.getDescription())));

        verify(genreService, times(1)).findAll();
        verifyNoMoreInteractions(genreService);
    }

}