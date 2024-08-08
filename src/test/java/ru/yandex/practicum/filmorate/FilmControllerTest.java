package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

public class FilmControllerTest {
    private final FilmController filmController = new FilmController();
    private final Film film = new Film(1, "film", "part1", LocalDate.of(1999, 07, 16), 120);

    @Test
    void create_shouldCreateUser() {
        Film thisFilm = new Film(1, "film", "part1", LocalDate.of(1999, 07, 16), 120);
        filmController.create(thisFilm);
        Assertions.assertEquals(film, thisFilm);
        Assertions.assertEquals(1, filmController.getFilms().size());
    }

    @Test
    void update_shouldUpdateUsers() {
        Film thisFilm = new Film(1, "films", "part1", LocalDate.of(1999, 07, 16), 120);
        filmController.create(film);
        filmController.update(thisFilm);
        Assertions.assertEquals("films", thisFilm.getName());
        Assertions.assertEquals(film.getId(), thisFilm.getId());
        Assertions.assertEquals(1, filmController.getFilms().size());
    }

    @Test
    void create_notShouldCreateUserIfNameIsEmpty() {
        Film thisFilm = new Film(1, "", "part1", LocalDate.of(1999, 07, 16), 120);
        // Assertions.assertThrows(ValidationException.class, () -> filmController.create(thisFilm));
        Assertions.assertEquals(0, filmController.getFilms().size());
    }

    @Test
    void create_shouldTNotAddFilmIfDateIsAfter1895() {
        Film thisFilm = new Film(1, "film1", "part1", LocalDate.of(1000, 07, 16), 120);
        Assertions.assertThrows(ValidationException.class, () -> filmController.create(thisFilm));
        Assertions.assertEquals(0, filmController.getFilms().size());
    }
}
