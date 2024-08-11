package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
public class User {
    @Positive
    private Long id;
    private String name;
    @NotBlank
    private String login;
    @NotBlank
    private String email;
    private LocalDate birthday;
    private Set<Long> friendId;

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

