package com.philippabather.properpropertiesapi.exception;

import static com.philippabather.properpropertiesapi.constants.ErrorMessages.LOGIN_INVALID;

public class InvalidLoginException extends RuntimeException {

    public InvalidLoginException(String username) {
        super(LOGIN_INVALID + username);
    }
}
