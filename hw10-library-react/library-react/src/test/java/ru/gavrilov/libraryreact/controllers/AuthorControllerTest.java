package ru.gavrilov.libraryreact.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.gavrilov.libraryreact.mappers.AuthorMapper;
import ru.gavrilov.libraryreact.model.Author;
import ru.gavrilov.libraryreact.service.AuthorService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {AuthorController.class, AuthorMapper.class})
@DisplayName("Author controller's methods should ")
class AuthorControllerTest {

    private static final String API = "/api/v1";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AuthorService authorService;

    @Autowired
    private AuthorMapper authorMapper;

    private List<Author> authorList;
    private Author author;
    private Author author2;

    @BeforeEach
    void init() {
        this.author = new Author("Александр", "Пушкин");
        this.author2 = new Author("Федор", "Достаевский");
        this.authorList = Arrays.asList(author, author2);
    }

    @Test
    @DisplayName("return json array of author when a GET request was made for '/authors'")
    void getAllAuthors() throws Exception {
        when(authorService.findAll()).thenReturn(authorList);

        mockMvc.perform(get(API + "/authors"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(author.getId())))
                .andExpect(jsonPath("$[0].firstName", is(author.getFirstName())))
                .andExpect(jsonPath("$[0].lastName", is(author.getLastName())))
                .andExpect(jsonPath("$[1].id", is(author2.getId())))
                .andExpect(jsonPath("$[1].firstName", is(author2.getFirstName())))
                .andExpect(jsonPath("$[1].lastName", is(author2.getLastName())));

        verify(authorService, times(1)).findAll();
        verifyNoMoreInteractions(authorService);
    }

}