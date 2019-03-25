package ru.gavrilov.service.user;

import ru.gavrilov.model.User;

public interface UserService {

    User saveUser(String lastName, String firstName);

    User getUserByLastName(String lastName);

    User getUserById(Integer id);
}
