package ru.gavrilov.service.user;

import ru.gavrilov.model.User;

import java.util.List;

public interface UserService {

    User saveUser(String lastName, String firstName);

    User getUserByLastName(String lastName);

    User getUserById(Integer id);

    List<User> getAll();
}
