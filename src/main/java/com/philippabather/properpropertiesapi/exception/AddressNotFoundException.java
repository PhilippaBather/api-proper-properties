package com.philippabather.properpropertiesapi.exception;

import static com.philippabather.properpropertiesapi.constants.ErrorMessages.ADDRESS_NOT_FOUND_EXCEPTION_MSG;

/**
 * AddressNotFoundException - maneja el error lanzado cuando una entidad del tipo Address ('dirección') no está encontrado
 * por su ID.
 *
 * @author Philippa Bather
 */
public class AddressNotFoundException extends RuntimeException {

    public AddressNotFoundException(long addressId) {
        super(ADDRESS_NOT_FOUND_EXCEPTION_MSG + addressId);
    }
}
