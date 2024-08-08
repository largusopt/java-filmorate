package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private HashMap<Integer, User> users = new HashMap<>();
    private int id = 0;

    @PostMapping // эндпоинт добавления пользователя
    public User create(@RequestBody User user) {
        userValidation(user);
        user.setId(++id);
        users.put(user.getId(), user);
        log.info("'{}' был добавлен в библиотеку, индификатор пользователя'{}'", user.getName(), user.getId());
        return user;
    }

    @PutMapping //эндпоинт обновления
    public User update(@RequestBody User user) {
        if (users.containsKey(user.getId())) {
            userValidation(user);
            users.put(user.getId(), user);
            log.info("'{}'пользователь был успешно обновлен, идентификатор пользователя '{}'", user.getName(), user.getId());
        } else {
            throw new ValidationException("Невозможно обновить несуществующего пользователя");
        }
        return user;
    }

    @GetMapping
    public List<User> getUsers() {
        log.info("Сейчас доступно пользователей", users.size());
        return new ArrayList<>(users.values());
    }

    public void userValidation(User user) {
        if (user.getEmail().isBlank() || user.getEmail().isEmpty() || !user.getEmail().contains("@")) {
            throw new ValidationException("Неккоретный email");
        }
        if (user.getLogin().isBlank() || user.getLogin().isEmpty()) {
            throw new ValidationException("Неккоректный логин");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.info("Имя пользователя с идентификатором '{}' было установлена на '{}'", user.getId(), user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now()) || user.getBirthday() == null) {
            throw new ValidationException("Неккоретная дата рождения");
        }
    }
}