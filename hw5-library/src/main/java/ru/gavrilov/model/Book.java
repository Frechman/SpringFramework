package ru.gavrilov.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@AllArgsConstructor
@NoArgsConstructor
public class Book {

    private String isbn;
    private String title;
    private Long publishYear;
    private Long genreId;
    private Long authorId;

    @Override
    public String toString() {
        return "\"" + title + "\"" +
                ", " + publishYear + "г." +
                ", isbn:" + isbn;
    }
}
