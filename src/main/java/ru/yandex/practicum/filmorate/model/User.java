package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class User {
    @Positive
    private Long id;
    @NotBlank
    private String email;
    @NotBlank
    private String login;
    private String name;
    private LocalDate birthday;
    private Set<Long> friendId = new HashSet<>();

    public void setFriendId(Long id) {
        friendId.add(id);
    }

    public void deleteFrindById(Long id) {
        friendId.remove(id);
    }

    public int getFriendsQuantity() {
        return friendId.size();
    }
}

