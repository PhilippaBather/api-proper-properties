package com.philippabather.properpropertiesapi.exception;

import static com.philippabather.properpropertiesapi.constants.ErrorMessages.CLIENT_NOT_FOUND_MSG;

/**
 * ClientNotFoundException - maneja el error cuando una entidad del tipo Client no está encontrado por su ID.
 *
 * @author Philippa Bather
 */
public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException(long clientId) {
        super(CLIENT_NOT_FOUND_MSG + clientId);
    }
}
