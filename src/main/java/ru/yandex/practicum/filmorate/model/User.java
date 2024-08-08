package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class User {
    int id;
    @NotBlank
    String email;
    @NotBlank
    String login;
    String name;
    @Past
    LocalDate birthday;
}

