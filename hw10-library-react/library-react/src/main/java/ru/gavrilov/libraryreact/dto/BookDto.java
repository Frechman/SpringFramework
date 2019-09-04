package ru.gavrilov.libraryreact.dto;

import lombok.Data;

@Data
public class BookDto {

    private Long id;
    private String isbn;
    private String title;
    private Long publishYear;
    private GenreDto genre;
    private AuthorDto author;

}
