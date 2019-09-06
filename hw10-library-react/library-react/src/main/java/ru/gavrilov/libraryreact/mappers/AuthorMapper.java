package ru.gavrilov.libraryreact.mappers;

import org.mapstruct.Mapper;
import ru.gavrilov.libraryreact.dto.AuthorDto;
import ru.gavrilov.libraryreact.model.Author;

import java.util.List;

@Mapper
public interface AuthorMapper {

    AuthorDto authorToAuthorDto(Author author);

    List<AuthorDto> toAuthorsDto(List<Author> authors);

    Author toAuthor(AuthorDto dto);
}
