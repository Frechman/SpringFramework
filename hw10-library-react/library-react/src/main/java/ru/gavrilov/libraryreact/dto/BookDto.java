package ru.gavrilov.libraryreact.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private Long id;
    private String isbn;
    private String title;
    private Long publishYear;
    private GenreDto genre;
    private AuthorDto author;

}
