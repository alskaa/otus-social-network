package ru.otus.socialnetwork.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.otus.socialnetwork.exception.NotFoundException;
import ru.otus.socialnetwork.exception.UsernameAlreadyInUseException;

@RestControllerAdvice
public class DefaultAdvice {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleException(NotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameAlreadyInUseException.class)
    public ResponseEntity<?> handleException(UsernameAlreadyInUseException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

}