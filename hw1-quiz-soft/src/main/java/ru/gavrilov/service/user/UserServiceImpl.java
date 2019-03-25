package ru.gavrilov.service.user;

import ru.gavrilov.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    private final List<User> userStore = new ArrayList<>();
    private int countUser = 0;

    @Override
    public User saveUser(String lastName, String firstName) {
        User user = new User(++countUser, lastName, firstName);
        userStore.add(user);
        return user;
    }

    @Override
    public User getUserByLastName(String lastName) {
        return this.userStore.stream()
                .filter(user -> user.getLastName().equals(lastName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public User getUserById(Integer id) {
        return userStore.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
