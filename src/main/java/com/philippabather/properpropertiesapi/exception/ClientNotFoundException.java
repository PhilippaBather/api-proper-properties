package com.philippabather.properpropertiesapi.exception;

import static com.philippabather.properpropertiesapi.constants.ErrorMessages.CLIENT_NOT_FOUND_EXCEPTION_MSG;

/**
 * ClientNotFoundException - maneja el error lanzado cuando una entidad del tipo Client ('cliente') no está encontrado
 * por su ID.
 *
 * @author Philippa Bather
 */
public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException(long clientId) {
        super(CLIENT_NOT_FOUND_EXCEPTION_MSG + clientId);
    }
}
