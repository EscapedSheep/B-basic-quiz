package com.thoughtworks.gtb.basic.quiz.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> ParamNotValidHandler(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getFieldError().getDefaultMessage();
        Error error = Error.builder()
                .timestamp(new Date())
                .error(HttpStatus.BAD_REQUEST.name())
                .status(HttpStatus.BAD_REQUEST.value())
                .message(message).build();
        return ResponseEntity.badRequest().body(error);
    }
}
