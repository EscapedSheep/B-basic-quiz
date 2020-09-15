package com.thoughtworks.gtb.basic.quiz.exception;

import static com.thoughtworks.gtb.basic.quiz.exception.ErrorMessage.USER_NOT_FOUND;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException() {
        super(USER_NOT_FOUND);
    }
}
