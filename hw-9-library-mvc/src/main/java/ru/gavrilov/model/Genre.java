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
@Table(name = "GENRE")
public class Genre extends AbstractEntity {

    @NonNull
    @Column(name = "NAME")
    private String name;

    @NonNull
    @Column(name = "DESCRIPTION")
    private String description;

    @Override
    public String toString() {
        return String.format("Жанр: %s. Описание: %s", name, description);
    }
}
