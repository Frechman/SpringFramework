package ru.gavrilov.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "isbn")
@Entity
@Table(name = "BOOK")
public class Book {

    @Id
    @Column(name = "isbn")
    private String isbn;

    @Column(name = "title")
    private String title;

    @Column(name = "publish_year")
    private long publishYear;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book")
    private Set<Comment> comments;

    @Override
    public String toString() {
        return String.format("Книга \"%s\". %s, %d. isbn:%s", title, author, publishYear, isbn);
    }
}
