package ru.gavrilov.libraryreact.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gavrilov.libraryreact.dto.GenreDto;
import ru.gavrilov.libraryreact.mappers.GenreMapper;
import ru.gavrilov.libraryreact.service.GenreService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class GenreController {

    private final GenreService genreService;
    private final GenreMapper genreMapper;

    @Autowired
    public GenreController(GenreService genreService, GenreMapper genreMapper) {
        this.genreService = genreService;
        this.genreMapper = genreMapper;
    }

    @GetMapping("/genres")
    public ResponseEntity<List<GenreDto>> getAllGenres() {
        return ResponseEntity.ok(genreMapper.toGenresDto(genreService.findAll()));
    }

}
