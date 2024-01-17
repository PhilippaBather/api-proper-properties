package com.philippabather.properpropertiesapi.exception;

import static com.philippabather.properpropertiesapi.constants.ErrorMessages.LOGIN_INVALID;

/**
 * InvalidLoginException - maneja el error lanzado cuando las detalles de login son incorrectos.
 *
 * @author Philippa Bather
 */
public class InvalidLoginException extends RuntimeException {

    public InvalidLoginException(String username) {
        super(LOGIN_INVALID + username);
    }
}
