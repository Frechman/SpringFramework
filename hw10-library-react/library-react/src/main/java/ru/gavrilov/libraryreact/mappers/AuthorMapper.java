package ru.gavrilov.libraryreact.mappers;

import org.mapstruct.Mapper;
import ru.gavrilov.libraryreact.dto.AuthorDto;
import ru.gavrilov.libraryreact.model.Author;

@Mapper
public interface AuthorMapper {

    AuthorDto authorToAuthorDto(Author author);

    Author toAuthor(AuthorDto dto);
}
