package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class Film {
    private int id;
    @NonNull
    @NotBlank(message = "Невозможно добавить фильм без названия")
    private String name;

    @NonNull
    @Size(min = 1, max = 200, message = "Описание должно быть не более 200 символов/ не может быть равно 0")
    private String description;
    private LocalDate releaseDate;

    @Positive(message = "Продолжительность фильма должна быть положительной")
    private long duration;
}
