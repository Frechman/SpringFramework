package ru.gavrilov.libraryreact.mappers;

import org.mapstruct.Mapper;
import ru.gavrilov.libraryreact.dto.GenreDto;
import ru.gavrilov.libraryreact.model.Genre;

@Mapper
public interface GenreMapper {

    GenreDto genreToGenreDto(Genre genre);

    Genre toGenre(GenreDto dto);
}
