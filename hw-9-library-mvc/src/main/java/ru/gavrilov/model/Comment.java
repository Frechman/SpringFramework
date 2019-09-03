package ru.gavrilov.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "COMMENT")
public class Comment extends AbstractEntity {

    @Column(name = "CONTENT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "BOOK_ISBN")
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
