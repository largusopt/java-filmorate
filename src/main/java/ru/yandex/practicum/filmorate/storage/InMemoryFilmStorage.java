package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final HashMap<Long, Film> films = new HashMap<Long, Film>();

    @Override
    public Film createFilm(Film film) {
        filmValidation(film);
        films.put(film.getId(), film);
        log.info("'{}' был добавлен в библиотеку, индификатор фильма'{}'", film.getName(), film.getId());
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        if (films.containsKey(film.getId())) {
            filmValidation(film);
            films.put(film.getId(), film);
            log.info("'{}'фильм был успешно обновлен, идентификатор фильма '{}'", film.getName(), film.getId());
        } else {
            throw new ValidationException("Невозможно обновить несуществующий фильм");
        }
        return film;
    }

    @Override
    public List<Film> getFilms() {
        log.info("Сейчас доступно", films.size());
        return new ArrayList<>(films.values());
    }

    @Override
    public void deleteFilms() {
        films.clear();
        log.info("Список фильмов успешно очищен");
    }

    @Override
    public Film getFilmById(Long id) {
        if (films.containsKey(id)) {
            return films.get(id);
        } else {
            throw new ObjectNotFoundException("Фильма с таким id не существует");
        }
    }

    @Override
    public void filmValidation(Film film) {
        if (film.getDescription().length() > 200 || film.getDescription().isEmpty()) {
            throw new ValidationException("Описание должно быть не более 200 символов/ не может быть равно 0");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Некоретная дата фильма");
        }
    }


}
