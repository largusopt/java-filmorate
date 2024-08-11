package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    User create(User user);

    void deleteUsers();

    User update(User user);

    List<User> getUsers();

    User getUsersById(Long id);
}
