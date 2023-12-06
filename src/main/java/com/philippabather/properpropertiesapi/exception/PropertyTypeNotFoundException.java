package com.philippabather.properpropertiesapi.exception;

import static com.philippabather.properpropertiesapi.constants.ErrorMessages.PROPERTY_TYPE_NOT_FOUND_EXCEPTION_MSG;

/**
 * PropertyNotFoundException - maneja el error lanzado cuando un PropertyType (tipo de propiedad) no está encontrado.
 * PropertyType refiere al tipo de inmueblo: comercial, piso, o casa.
 *
 * @author Philippa Bather
 */
public class PropertyTypeNotFoundException extends RuntimeException {

    public PropertyTypeNotFoundException(String propertyType) {
        super(String.format(propertyType + PROPERTY_TYPE_NOT_FOUND_EXCEPTION_MSG));
    }
}
