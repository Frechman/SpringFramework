package ru.gavrilov.model;

public class User {

    private final Integer id;
    private final String lastName;
    private final String firstName;

    public User(Integer id, String lastName, String firstName) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public Integer getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getFullName() {
        return String.format("%s %s", lastName, firstName);
    }

    @Override
    public String toString() {
        return String.format("User[lastName='%s', firstName='%s']", lastName, firstName);
    }
}
