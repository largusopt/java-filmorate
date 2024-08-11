package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserService userService;

    public void addLike(Long filmId, Long userId) {
        Film film = filmStorage.getFilmById(filmId);
        if (film == null) {
            throw new ObjectNotFoundException("Данного фильма не существует");
        }
        film.addLike(userId);
        log.info("Поставлен лайк на фильм '{}' пользователем '{}'", film.getName(), userService.getUserStorage().getUsersById(userId));
    }

    public void dislike(Long filmId, Long userId) {
        Film film = filmStorage.getFilmById(filmId);
        if (film == null) {
            throw new ObjectNotFoundException("Данного фильма не существует");
        }
        film.removeLike(userId);
        log.info("Лайк с фильма '{}' удален пользователем '{}'", film.getName(), userService.getUserStorage().getUsersById(userId));
    }

    public List<Film> topPopular(int count) {
        log.info("Загрузка 10 популярных фильмов по количесву лайков");
        return filmStorage.getFilms().stream()
                .sorted(Comparator.comparingInt(Film::getLikesQuantity).reversed())
                .limit(count).collect(Collectors.toList());
    }
}
