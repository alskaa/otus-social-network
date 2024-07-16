package ru.otus.socialnetwork.model.exception;

import lombok.Getter;

/**
 * <p>Unchecked exception to inform that object not found</p>
 */
public class NotFoundException extends RuntimeException {
    private static final String MESSAGE = "Not found!";
    @Getter
    private final Object id;

    public NotFoundException(String message, Object id) {
        super(message);
        this.id = id;
    }

    public NotFoundException(Object id) {
        super(MESSAGE);
        this.id = id;
    }
}
