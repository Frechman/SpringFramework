package ru.gavrilov.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(of = "uuid")
@NoArgsConstructor
@Entity
@Table(name = "COMMENT")
public class Comment {

    public Comment(String content, Book book) {
        this.content = content;
        this.book = book;
    }

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "uuid")
    private String uuid;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "book_isbn")
    private Book book;

    @Override
    public String toString() {
        return String.format("Комментарий \"%s\" к %s", content, book.toString());
    }
}
