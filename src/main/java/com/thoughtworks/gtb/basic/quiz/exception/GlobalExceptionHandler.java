package com.thoughtworks.gtb.basic.quiz.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Error> UserNotFoundHandler(UserNotFoundException exception) {
        Error error = Error.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(exception.getMessage())
                .error(HttpStatus.NOT_FOUND.name())
                .timestamp(new Date())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
