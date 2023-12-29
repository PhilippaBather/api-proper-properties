package com.philippabather.properpropertiesapi.exception;

import static com.philippabather.properpropertiesapi.constants.ErrorMessages.PROPERTY_TYPE_NOT_FOUND_EXCEPTION_MSG;

/**
 * PropertyNotFoundException - maneja el error lanzado cuando un String de un PropertyType ('tipo de inmueblen) no
 * corresponde con ningún enum del tipo PropertyType ('tipo de inmueble).
 *
 * PropertyType refiere al tipo de inmueblo: comercial, piso, o casa.
 *
 * @author Philippa Bather
 */
public class PropertyTypeNotFoundException extends RuntimeException {

    public PropertyTypeNotFoundException(String propertyType) {
        super(String.format(propertyType + PROPERTY_TYPE_NOT_FOUND_EXCEPTION_MSG));
    }
}
