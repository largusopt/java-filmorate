package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;

public class UserControllerTest {
    private final UserStorage userStorage = new InMemoryUserStorage();
    private final User user = new User(null, "Tanya", "user", "tata.52@ya.ru", LocalDate.of(1999, 07, 16));
    private final User newUser = new User(null, "Savva", "userok", "mtv.52@ya.ru", LocalDate.of(1999, 07, 16));
    private final User userWithoutEmail = new User(1L, "Savva", "userok", "", LocalDate.of(1999, 07, 16));
    private final InMemoryUserStorage storage = new InMemoryUserStorage();
    private final UserService service = new UserService(storage);
    private final UserController controller = new UserController(storage, service);

    @AfterEach
    public void afterEach() {
        storage.deleteUsers();
    }

    @Test
    void create_shouldCreateUser() {
        controller.create(user);
        Assertions.assertEquals(1, controller.getUsers().size());
    }

    @Test
    void update_shouldUpdateUser() {
        User thisUser = new User(2L, "Tanya", "user", "tata.52@ya.ru", LocalDate.of(1999, 07, 16));
        controller.create(user);
        controller.update(thisUser);
        Assertions.assertEquals("tata.52@ya.ru", thisUser.getEmail());
        Assertions.assertEquals(user.getId(), thisUser.getId());
        Assertions.assertEquals(1, controller.getUsers().size());
    }

    @Test
    void create_shouldCreateUserIfNameIsEmpty() {
        User thisUser = new User(1L, "", "userok", "mtv.52@ya.ru", LocalDate.of(1999, 07, 16));
        controller.create(thisUser);
        Assertions.assertEquals("userok", thisUser.getName());
        Assertions.assertEquals(1, controller.getUsers().size());
    }

    @Test
    void create_shouldCreateUserIfIdIsEmpty() {
        User thisUser = new User(null, "Tanya", "user", "tata.52@ya.ru", LocalDate.of(1999, 07, 16));
        controller.create(thisUser);
        User tanya = new User(null, "Savva", "culcurry", "mtv.52@ya.ru", LocalDate.of(1999, 07, 16));
        controller.create(tanya);
        Assertions.assertEquals(2, thisUser.getId());
        Assertions.assertEquals(3, tanya.getId());
        Assertions.assertEquals(2, controller.getUsers().size());
    }

    @Test
    void create_shouldThrowExceptionIfEmailIncorrect() {
        user.setEmail("mtv.52.yan.ru");
        Assertions.assertThrows(ValidationException.class, () -> controller.create(user));
        Assertions.assertEquals(0, controller.getUsers().size());
    }

    @Test
    void create_shouldThrowExceptionIfEmailsIsEmpty() {
        Assertions.assertThrows(ValidationException.class, () -> controller.create(userWithoutEmail));
        Assertions.assertEquals(0, controller.getUsers().size());
    }

    @Test
    void create_shouldThrowExceptionIfDateIsAfterNow() {
        user.setBirthday(LocalDate.of(2030, 07, 16));
        Assertions.assertThrows(ValidationException.class, () -> controller.create(user));
        Assertions.assertEquals(0, controller.getUsers().size());
    }

    @Test
    void addFriend_shouldAddFriend() {
        controller.create(user);
        controller.create(newUser);
        controller.addFriend(user.getId(), newUser.getId());
        Assertions.assertEquals(1, user.getFriendId().size());
        Assertions.assertEquals(1, newUser.getFriendId().size());
    }

    @Test
    void deleteFriend_shouldDeleteFrind() {
        controller.create(user);
        controller.create(newUser);
        controller.deleteFriendById(user.getId(), newUser.getId());
        Assertions.assertEquals(0, user.getFriendId().size());
        Assertions.assertEquals(0, newUser.getFriendId().size());
    }
}