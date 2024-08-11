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
import java.util.HashSet;

public class UserControllerTest {
    private final UserStorage userStorage = new InMemoryUserStorage();
    private final User user = new User(1L, "mtv.52@ya.ru", "user", "user", LocalDate.of(1999, 07, 16), new HashSet<>());
    private final User newUser = new User(2L, "mvm.52@ya.ru", "frinend", "Ole", LocalDate.of(2000, 07, 16), new HashSet<>());
    private final User userWithoutEmail = new User(1L, "", "user", "user", LocalDate.of(1999, 07, 16), new HashSet<>());
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
        User thisUser = new User(1L, "tata.52@ya.ru", "user", "user", LocalDate.of(1999, 07, 16), new HashSet<>());
        controller.create(user);
        controller.update(thisUser);
        Assertions.assertEquals("tata.52@ya.ru", thisUser.getEmail());
        Assertions.assertEquals(user.getId(), thisUser.getId());
        Assertions.assertEquals(1, controller.getUsers().size());
    }

    @Test
    void create_shouldCreateUserIfNameIsEmpty() {
        User thisUser = new User(1L, "tata.52@ya.ru", "user", null, LocalDate.of(1999, 07, 16), new HashSet<>());
        controller.create(thisUser);
        Assertions.assertEquals("user", thisUser.getName());
        Assertions.assertEquals(1, controller.getUsers().size());
    }

    @Test
    void create_shouldCreateUserIfIdIsEmpty() {
        User thisUser = new User(null, "tata.52@ya.ru", "user", null, LocalDate.of(1999, 07, 16), new HashSet<>());
        controller.create(thisUser);
        User tanya = new User(null, "tata.52@ya.ru", "Tanya", null, LocalDate.of(1999, 07, 16), new HashSet<>());
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