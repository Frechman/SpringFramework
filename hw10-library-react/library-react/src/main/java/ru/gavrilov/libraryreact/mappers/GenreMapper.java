package ru.gavrilov.libraryreact.mappers;

import org.mapstruct.Mapper;
import ru.gavrilov.libraryreact.dto.GenreDto;
import ru.gavrilov.libraryreact.model.Genre;

import java.util.List;

@Mapper
public interface GenreMapper {

    GenreDto genreToGenreDto(Genre genre);

    List<GenreDto> toGenresDto(List<Genre> genres);

    Genre toGenre(GenreDto dto);
}
