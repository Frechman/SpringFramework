package ru.gavrilov.libraryreact.mappers;

import org.mapstruct.Mapper;
import ru.gavrilov.libraryreact.dto.BookDto;
import ru.gavrilov.libraryreact.model.Book;

import java.util.List;

@Mapper(uses = {AuthorMapper.class, GenreMapper.class})
public interface BookMapper {

    BookDto toBookDto(Book book);

    List<BookDto> toBooksDto(List<Book> books);

    Book toBook(BookDto dto);
}

