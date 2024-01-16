package com.philippabather.properpropertiesapi.exception;

import static com.philippabather.properpropertiesapi.constants.ErrorMessages.REGISTRATION_EXCEPTION_USERNAME_EXISTS_MSG;

/**
 * RegistrationException - maneja el error lanzado hay excepciones de registración.
 *
 * @author Philippa Bather
 */
public class RegistrationException extends RuntimeException {

    public RegistrationException(String username) {
        super(REGISTRATION_EXCEPTION_USERNAME_EXISTS_MSG + username);
    }
}
