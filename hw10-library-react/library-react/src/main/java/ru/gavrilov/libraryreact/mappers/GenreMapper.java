package ru.gavrilov.libraryreact.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.gavrilov.libraryreact.dto.GenreDto;
import ru.gavrilov.libraryreact.model.Genre;

@Mapper
public interface GenreMapper {

    GenreMapper INSTANCE = Mappers.getMapper(GenreMapper.class);

    GenreDto genreToGenreDto(Genre genre);
}
