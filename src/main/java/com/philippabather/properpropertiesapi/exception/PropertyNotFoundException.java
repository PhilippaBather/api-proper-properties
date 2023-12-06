package com.philippabather.properpropertiesapi.exception;

import static com.philippabather.properpropertiesapi.constants.ErrorMessages.PROPERTY_NOT_FOUND_EXCEPTION_MSG;

/**
 * PropertyNotFoundException - maneja el error lanzado cuando una entidad del tipo Property ('inmueble') no está
 * encontrado por su ID.
 *
 * @author Philippa Bather
 */
public class PropertyNotFoundException extends RuntimeException {

    public PropertyNotFoundException(long propertyId) {
        super(PROPERTY_NOT_FOUND_EXCEPTION_MSG + propertyId);
    }
}
