package ru.gavrilov.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author {

    private Long id;
    private String firstName;
    private String lastName;

    @Override
    public String toString() {
        return String.format("Author: %s %s", lastName, firstName);
    }
}
