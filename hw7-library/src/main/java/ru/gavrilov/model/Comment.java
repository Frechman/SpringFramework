package ru.gavrilov.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@EqualsAndHashCode(of = "uuid")
@Entity
public class Comment {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private long uuid;

    private String content;

    @ManyToOne
    @JoinColumn(name = "book_isbn")
    private Book book;

    @Override
    public String toString() {
        return String.format("Комментарий \"%s\" к %s", content, book.toString());
    }
}
