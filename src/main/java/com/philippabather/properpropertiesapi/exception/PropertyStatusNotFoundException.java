package com.philippabather.properpropertiesapi.exception;

import static com.philippabather.properpropertiesapi.constants.ErrorMessages.PROPERTY_STATUS_NOT_FOUND_EXCEPTION_MSG;

/**
 * PropertyStatusNotFoundException - maneja el error lanzado cuando un PropertyStatus (estado de mueblo) no está encontrado.
 * PropertyStatus refiere a si un inmueble es para alquiler o vender.
 *
 * @author Philippa Bather
 */
public class PropertyStatusNotFoundException extends RuntimeException {

    public PropertyStatusNotFoundException(String propertyStatus) {
        super(propertyStatus + PROPERTY_STATUS_NOT_FOUND_EXCEPTION_MSG);
    }
}
