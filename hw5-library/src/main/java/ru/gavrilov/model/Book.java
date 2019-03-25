package ru.gavrilov.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Book {

    private String isbn;
    private String title;
    private Long publishYear;
    private Long genreId;
    private List<Author> authors;

    public Book(String isbn, String title, Long publishYear, Long genreId) {
        this.isbn = isbn;
        this.title = title;
        this.publishYear = publishYear;
        this.genreId = genreId;
    }
}
