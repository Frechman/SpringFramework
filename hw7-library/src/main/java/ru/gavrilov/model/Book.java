package ru.gavrilov.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@EqualsAndHashCode(of = "isbn")
@Entity
public class Book {

    @Id
    private String isbn;

    private String title;

    private long publishYear;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @Override
    public String toString() {
        return String.format("Книга \"%s\". %s, %d. isbn:%s", title, author, publishYear, isbn);
    }
}