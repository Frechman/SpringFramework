package ru.gavrilov.service.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.gavrilov.model.User;

import static org.assertj.core.api.Assertions.assertThat;

class UserServiceImplTest {

    private UserServiceImpl userService;

    @BeforeEach
    private void init() {
        userService = new UserServiceImpl();
        userService.saveUser("test", "trest");
    }

    @Test
    @DisplayName("should return get expected user by last name")
    void testSaveUser() {
        User expected = new User(2, "trest", "another");
        User actual = userService.saveUser("trest", "another");

        assertThat(actual)
                .isNotSameAs(expected)
                .isEqualTo(expected);

        assertThat(userService.getUserByLastName(actual.getLastName()))
                .isEqualTo(expected);

        assertThat(userService.getUserById(1)).isNotEqualTo(expected);
    }

    @Test
    @DisplayName("should return expected user by last name")
    void testGetUserByLastName() {
        User expected = new User(1, "test", "trest");

        User actualUser = userService.getUserByLastName(expected.getLastName());

        assertThat(actualUser)
                .hasFieldOrPropertyWithValue("lastName", "test")
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("should return get expected user by id")
    void testGetUserById() {
        User expected = new User(1, "test", "trest");
        User userTwo = userService.saveUser("test2", "trest");

        User actualUser = userService.getUserById(expected.getId());

        assertThat(actualUser)
                .isEqualTo(expected)
                .isNotSameAs(expected)
                .hasFieldOrPropertyWithValue("lastName", "test")
                .hasFieldOrPropertyWithValue("firstName", "trest");

        assertThat(userService.getUserById(2))
                .isEqualTo(userTwo);
    }
}