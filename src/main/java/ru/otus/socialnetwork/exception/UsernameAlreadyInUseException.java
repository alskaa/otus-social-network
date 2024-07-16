package ru.otus.socialnetwork.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

    private final String message;

    public NotFoundException(Long id) {
        this.message = String.format("Entity with id %s not found", id);
    }
}
