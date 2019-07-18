package ru.gavrilov.model;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    private String isbn;
    private String title;
    private long publishYear;
    private Genre genre;
    private Author author;

    @Override
    public String toString() {
        return "\"" + title + "\"" +
                ", " + publishYear + "г." +
                ", isbn:" + isbn +
                " " + author +
                " " + genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        return Objects.equals(isbn, book.isbn);
    }

    @Override
    public int hashCode() {
        return isbn != null ? isbn.hashCode() : 0;
    }
}
