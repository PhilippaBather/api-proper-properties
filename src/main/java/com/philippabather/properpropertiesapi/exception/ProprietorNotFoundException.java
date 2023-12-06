package com.philippabather.properpropertiesapi.exception;

import static com.philippabather.properpropertiesapi.constants.ErrorMessages.PROPRIETOR_NOT_FOUND_EXCEPTION_MSG;

/**
 * ProprietorNotFoundException - maneja el error lanzado cuando una entidad del tipo Proprietor ('propietario') no está
 * encontrado por su ID.
 *
 * @author Philippa Bather
 */
public class ProprietorNotFoundException extends RuntimeException {

    public ProprietorNotFoundException(long proprietorId) {
        super(PROPRIETOR_NOT_FOUND_EXCEPTION_MSG + proprietorId);
    }
}
