package ru.gavrilov.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Genre {

    private long id;
    private String name;
    private String description;

    @Override
    public String toString() {
        return String.format("Жанр: %s. Описание: %s", name, description);
    }
}
