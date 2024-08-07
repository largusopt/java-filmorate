package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class User {
    int id;
    @NotBlank(message = "Неккоретный email")
    String email;
    @NotBlank(message = "Неккоректный логин")
    String login;
    String name;
    @Past(message = "Неккоретная дата рождения")
    LocalDate birthday;
}

