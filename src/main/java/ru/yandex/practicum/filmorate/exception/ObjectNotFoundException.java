package ru.yandex.practicum.filmorate.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ObjectNotFoundException extends IllegalArgumentException {
    public ObjectNotFoundException(String message) {
        super(message);
        log.error(message);
    }
}
