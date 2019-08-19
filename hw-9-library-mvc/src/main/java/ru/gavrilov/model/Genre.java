package ru.gavrilov.model;

import lombok.*;

import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Genre extends AbstractEntity {

    @NonNull
    private String name;

    @NonNull
    private String description;

    @Override
    public String toString() {
        return String.format("Жанр: %s. Описание: %s", name, description);
    }
}
