package ru.gavrilov.libraryreact.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.gavrilov.libraryreact.dto.BookDto;
import ru.gavrilov.libraryreact.model.Book;

@Mapper(uses = {AuthorMapper.class, GenreMapper.class})
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    BookDto bookToBookDto(Book book);
}

