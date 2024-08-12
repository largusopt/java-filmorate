package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
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
    @NotBlank(message = "Логин не может быть пустым")
    private String login;
    @NotBlank(message = "Email не может быть пустым")
    private String email;
    @Past(message = "Дата рождения не может быть в прошлом")
    @NotNull(message = "Дата рождения не может быть null")
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
    } //я добавила аннотации, и добавила обработчик исключений, но почему то не работает выброс нужной мне ошибки

    public void deleteFrindById(Long id) {
        friendId.remove(id);
    }
}


