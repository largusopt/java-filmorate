package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;

    public void addFriend(Long userId, Long friendId) {

        User user = userStorage.getUsersById(userId);
        if (user == null) {
            throw new ObjectNotFoundException("Такого имени не существует");
        }
        User friend = userStorage.getUsersById(friendId);
        if (friend == null) {
            throw new ObjectNotFoundException("Такого имени не существует");
        }
        friend.setFriendId(userId);
        user.setFriendId(friendId);
        log.info("{} был добавлен в друзья к пользлвателю {}", userStorage.getUsersById(friendId), userStorage.getUsersById(userId));

    }

    public void deleteFriendById(Long userId, Long friendId) {
        userStorage.getUsersById(userId).deleteFrindById(friendId);
        userStorage.getUsersById(friendId).deleteFrindById(userId);
        log.info("{} удален у пользователя {} ", userStorage.getUsersById(userId), userStorage.getUsersById(friendId));
    }

    public List<User> showFriends(Long userId) {
        User user = userStorage.getUsersById(userId);
        if (user == null) {
            throw new ObjectNotFoundException("Пользователь с ID '" + userId + "' не найден");
        }

        Set<Long> friendIds = user.getFriendId(); // Получаем список ID друзей

        if (friendIds.isEmpty()) {
            return Collections.emptyList();
        }
        return friendIds.stream()
                .map(userStorage::getUsersById)
                .collect(Collectors.toList());
    }

    public List<User> getCommonFriends(Long userId, Long friendId) {
        User user = userStorage.getUsersById(userId);
        User friend = userStorage.getUsersById(friendId);
        Set<Long> userFriends = user.getFriendId();
        Set<Long> friendFriends = friend.getFriendId();
        log.info("'{}' requested common friends' list with user '{}'", userId, friendId);
        if (userFriends.stream().anyMatch(friendFriends::contains)) {
            return userFriends.stream()
                    .filter(userFriends::contains)
                    .filter(friendFriends::contains)
                    .map(userStorage::getUsersById).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    public UserStorage getUserStorage() {
        return userStorage;
    }
}
