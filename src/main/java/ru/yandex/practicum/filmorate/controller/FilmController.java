package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController //создала класс контроллер
@RequestMapping("/films")
public class FilmController {

    private HashMap<Integer, Film> films = new HashMap<>();
    private int id = 0;

    @PostMapping // эндпоинт добавления фильма
    public Film create(@Valid @RequestBody Film film) { //я не смогла добавить почему то @Valid
        filmValidation(film);
        film.setId(++id);
        films.put(film.getId(), film);
        log.info("'{}' был добавлен в библиотеку, индификатор фильма'{}'", film.getName(), film.getId());
        return film;
    }

    @PutMapping //эндпоинт обновления
    public Film update(@Valid @RequestBody Film film) {
        if (films.containsKey(film.getId())) {
            filmValidation(film);
            films.put(film.getId(), film);
            log.info("'{}'фильм был успешно обновлен, идентификатор фильма '{}'", film.getName(), film.getId());
        } else {
            throw new ValidationException("Невозможно обновить несуществующий фильм");
        }
        return film;
    }

    @GetMapping
    public List<Film> getFilms() {
        log.info("Сейчас доступно", films.size());
        return new ArrayList<>(films.values());
    }

    public void filmValidation(Film film) {
        if (film.getName().isEmpty() || film.getName().isBlank()) {
         throw new ValidationException("Невозможно добавить фильм без названия");
        }// я не поняла как примениь аннотацию NotBank, но я старалась ))
        if (film.getDescription().length() > 200 || film.getDescription().length() == 0) {
            throw new ValidationException("Описание должно быть не более 200 символов/ не может быть равно 0");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Некоретная дата фильма");
        }
        if (film.getDuration() <= 0) {
            throw new ValidationException("Продолжительность фильма должна быть положительной");
         }
    }
}

