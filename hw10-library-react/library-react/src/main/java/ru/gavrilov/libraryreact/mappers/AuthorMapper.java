package ru.gavrilov.libraryreact.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.gavrilov.libraryreact.dto.AuthorDto;
import ru.gavrilov.libraryreact.model.Author;

@Mapper
public interface AuthorMapper {

    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    AuthorDto authorToAuthorDto(Author author);
}
