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
    private HashMap<Long, User> users = new HashMap<>();

    @Override
    public User create(User user) {
        userValidation(user);
        users.put(user.getId(), user);
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
            users.put((long) user.getId(), user);
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
        if (!users.containsKey(id)) {
            throw new ObjectNotFoundException("Пользователь не найден");
        }
        return users.get(id);
    }

    public void userValidation(User user) {
        if (!user.getEmail().contains("@")) {
            throw new ValidationException("Неккоретный email");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.info("Имя пользователя с идентификатором '{}' было установлена на '{}'", user.getId(), user.getLogin());
        }
        if (user.getBirthday() == null || user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Неккоретная дата рождения");
        }
    }
}
