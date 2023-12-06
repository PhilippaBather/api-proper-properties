package com.philippabather.properpropertiesapi.exception;

public class PropertyStatusNotFoundException extends RuntimeException {

    public PropertyStatusNotFoundException(String propertyStatus) {
        super(String.format("Property status %s not found; valid statuses are RENTAL or SALE", propertyStatus));
    }
}
