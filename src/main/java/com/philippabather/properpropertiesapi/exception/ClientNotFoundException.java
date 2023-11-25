package com.philippabather.properpropertiesapi.exception;

/**
 * ClientNotFoundException - maneja el error cuando una entidad del tipo Client no está encontrado por su ID.
 *
 * @author Philippa Bather
 */
public class ClientNotFoundException extends RuntimeException {
    private static final String CLIENT_NOT_FOUND_MSG = "Client not found with ID: ";

    public ClientNotFoundException() {
        super();
    }

    public ClientNotFoundException(long clientId) {
        super(CLIENT_NOT_FOUND_MSG + clientId);
    }
}
