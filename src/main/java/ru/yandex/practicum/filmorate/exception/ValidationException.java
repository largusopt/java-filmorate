package ru.yandex.practicum.filmorate.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidationException extends IllegalArgumentException {
    public ValidationException(final String massage) {
        final Logger log = LoggerFactory.getLogger(ValidationException.class);
        log.error(massage);
    }
}

