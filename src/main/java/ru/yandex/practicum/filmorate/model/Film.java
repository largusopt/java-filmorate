package ru.yandex.practicum.filmorate.model;

//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Positive;
//import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Film {
    private int id;
    @NonNull
    //@NotBlank
    private String name;

    @NonNull
    //@Size(min = 1, max = 200)
    private String description;
    private LocalDate releaseDate;

    //@Positive
    private long duration;
}
