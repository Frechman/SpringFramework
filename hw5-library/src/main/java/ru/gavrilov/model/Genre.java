package ru.gavrilov.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Genre {

    private Long id;
    private String name;
    private String description;

    public Genre(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
