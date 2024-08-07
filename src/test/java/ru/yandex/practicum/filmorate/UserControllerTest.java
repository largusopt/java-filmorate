package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class UserControllerTest {
    private final UserController userController = new UserController();
    private final User user = new User(1, "mtv.52@ya.ru", "user", "user", LocalDate.of(1999, 07, 16));

    @Test
    void create_shouldCreateUser() {
        User thisUser = new User(1, "mtv.52@ya.ru", "user", "user", LocalDate.of(1999, 07, 16));
        userController.create(thisUser);
        Assertions.assertEquals(user, thisUser);
        Assertions.assertEquals(1, userController.getUsers().size());
    }

    @Test
    void update_shouldUpdateUser() {
        User thisUser = new User(1, "tata.52@ya.ru", "user", "user", LocalDate.of(1999, 07, 16));
        userController.create(user);
        userController.update(thisUser);
        Assertions.assertEquals("tata.52@ya.ru", thisUser.getEmail());
        Assertions.assertEquals(user.getId(), thisUser.getId());
        Assertions.assertEquals(1, userController.getUsers().size());
    }

    @Test
    void create_shouldCreateUserIfNameIsEmpty() {
        User thisUser = new User(1, "tata.52@ya.ru", "user", null, LocalDate.of(1999, 07, 16));
        userController.create(thisUser);
        Assertions.assertEquals("user", thisUser.getName());
        Assertions.assertEquals(1, userController.getUsers().size());
    }

    @Test
    void create_shouldThrowExceptionIfEmailIncorrect() {
        user.setEmail("mtv.52.yan.ru");
        Assertions.assertThrows(ValidationException.class, () -> userController.create(user));
        Assertions.assertEquals(0, userController.getUsers().size());
    }
}
