package ru.gavrilov.libraryreact.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gavrilov.libraryreact.dto.AuthorDto;
import ru.gavrilov.libraryreact.mappers.AuthorMapper;
import ru.gavrilov.libraryreact.service.AuthorService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AuthorController {

    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    @Autowired
    public AuthorController(AuthorService authorService, AuthorMapper authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @GetMapping("/authors")
    public ResponseEntity<List<AuthorDto>> getAllAuthors() {
        return ResponseEntity.ok(authorMapper.toAuthorsDto(authorService.findAll()));
    }
}
