package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserStorage userStorage;
    private final UserService userService;

    @PostMapping // эндпоинт добавления пользователя
    public User create(@Valid @RequestBody User user) {
        userStorage.create(user);
        return user;
    }

    @PutMapping //эндпоинт обновления
    public User update(@Valid @RequestBody User user) {
        userStorage.update(user);
        return user;
    }

    @GetMapping
    public List<User> getUsers() {
        return userStorage.getUsers();
    }

    @PutMapping("/{userId}/friends/{friendId}")
    public void addFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        userService.addFriend(userId, friendId);
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public void deleteFriendById(@PathVariable Long userId, @PathVariable Long friendId) {
        userService.deleteFriendById(userId, friendId);
    }

    @GetMapping("/{userId}/friends")
    public List<User> showFriends(@PathVariable Long userId) {
        return userService.showFriends(userId);
    }

    @GetMapping("/{userId}/friends/common/{friendId}" +
            "")
    public List<User> getCommonFriends(@PathVariable Long userId, @PathVariable Long friendId) {
        return userService.getCommonFriends(userId, friendId);
    }
}