package ru.gavrilov.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Comment extends BaseEntity {

    private String content;

    @ManyToOne
    @JoinColumn(name = "book_isbn")
    private Book book;

    public Comment(String content, Book book) {
        this.content = content;
        this.book = book;
    }

    @Override
    public String toString() {
        return String.format("Комментарий \"%s\" к %s", content, book.toString());
    }
}
