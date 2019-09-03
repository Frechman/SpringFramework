package ru.gavrilov.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "AUTHOR")
public class Author extends AbstractEntity {

    @NonNull
    @Column(name = "FIRST_NAME")
    private String firstName;

    @NonNull
    @Column(name = "LAST_NAME")
    private String lastName;

    @Override
    public String toString() {
        return String.format("Автор: %s %s", lastName, firstName);
    }
}
