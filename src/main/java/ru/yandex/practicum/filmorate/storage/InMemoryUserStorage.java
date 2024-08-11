package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    private final HashMap<Long, User> users = new HashMap<>();
    private long id = 0;

    @Override
    public User create(User user) {
        userValidation(user);
        if (user.getId() == null) {
            user.setId(id);
            users.put(user.getId(), user);
        } else {
            users.put(user.getId(), user);
            id = user.getId();
        }
        log.info("'{}' был добавлен в библиотеку, индификатор пользователя'{}'", user.getName(), user.getId());
        return user;
    }

    @Override
    public void deleteUsers() {
        users.clear();
    }

    @Override
    public User update(User user) {
        if (users.containsKey(user.getId())) {
            userValidation(user);
            users.put(user.getId(), user);
            log.info("'{}'пользователь был успешно обновлен, идентификатор пользователя '{}'", user.getName(), user.getId());
            return user;
        } else {
            throw new ObjectNotFoundException("Невозможно обновить несуществующего пользователя");
        }
    }

    @Override
    public List<User> getUsers() {
        log.info("Сейчас доступно пользователей", users.size());
        return new ArrayList<>(users.values());
    }


    @Override
    public User getUsersById(Long id) {
        User user = users.get(id);
        if (user == null) {
            throw new ObjectNotFoundException("Пользователь не найден");
        }
        return users.get(id);
    }

    private void userValidation(User user) {
        if (user.getBirthday().isAfter(LocalDate.now()) || user.getBirthday() == null) {
            throw new ValidationException("Неккоректная дата рождения пользователя c ID '" + user.getId() + "'");
        }
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new ValidationException("неккоректный  email пользователя с ID '" + user.getId() + "'");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.info("ИМЯ ПОЛЬЗОВАТЕЛЯ С ИДЕНТИФИКАТОРОМ '{}' УСТАНОВЛЕНО на '{}'", user.getId(), user.getName());
        }
        if (user.getLogin().isBlank() || user.getLogin().isEmpty()) {
            throw new ValidationException("Неккоректный логин пользователя с идентификатором '" + user.getId() + "'");
        }
        //if (user.getFriendId() == null) {
        //  user.setFriendId(new HashSet<Long>());
        //}
        if (user.getId() == null || user.getId() <= 0) {
            user.setId(++id);
            log.info("Идентификатор пользователя '{}", user.getId());
        }

    }
}
