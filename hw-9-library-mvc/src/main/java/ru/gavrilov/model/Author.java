package ru.gavrilov.model;

import lombok.*;

import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Author extends AbstractEntity {

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @Override
    public String toString() {
        return String.format("Автор: %s %s", lastName, firstName);
    }
}
