package ru.gavrilov.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@EqualsAndHashCode(of = "uuid")
@NoArgsConstructor
@Entity
public class Comment {

    public Comment(String content, Book book) {
        this.content = content;
        this.book = book;
    }

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String uuid;

    private String content;

    @ManyToOne
    @JoinColumn(name = "book_isbn")
    private Book book;

    @Override
    public String toString() {
        return String.format("Комментарий \"%s\" к %s", content, book.toString());
    }
}
