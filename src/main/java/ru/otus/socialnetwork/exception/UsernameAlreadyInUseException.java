package ru.otus.socialnetwork.exception;

import lombok.Getter;

@Getter
public class UsernameAlreadyInUseException extends RuntimeException {

    private final String message;

    public UsernameAlreadyInUseException(String username) {
        this.message = String.format("Username %s already in use", username);
    }
}
