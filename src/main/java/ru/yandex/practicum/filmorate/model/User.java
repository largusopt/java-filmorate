package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
public class User {
    Long id;
    @NotBlank
    String email;
    @NotBlank
    String login;
    String name;
    LocalDate birthday;
    Set<Long> friendId;

    public void setFriendId(Long id) {
        friendId.add(id);
    }

    public void deleteFrindById(Long id) {
        friendId.remove(id);
    }
}

