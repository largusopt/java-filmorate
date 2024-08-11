package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
//@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String name;
    @NotBlank
    private String login;
    @NotBlank
    private String email;
    private LocalDate birthday;
    private Set<Long> friendId = new HashSet<>();

    public User(Long id, String name, String login, String email, LocalDate birthday) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.email = email;
        this.birthday = birthday;

    }

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

