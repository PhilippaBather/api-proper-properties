package com.philippabather.properpropertiesapi.exception;

public class PropertyTypeNotFoundException extends RuntimeException {

    public PropertyTypeNotFoundException(String propertyType) {
        super(String.format("Property type %s not found; valid types are: COMMERCIAL, FLAT, or HOUSE", propertyType));
    }
}
