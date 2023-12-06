package com.philippabather.properpropertiesapi.exception;

import static com.philippabather.properpropertiesapi.constants.ErrorMessages.REGION_NOT_FOUND_EXCEPTION_MSG;

/**
 * RegionNotFoundException - maneja el error lanzado cuando un String de una Region ('región') no corresponde con ningún enum
 * del tipo Region.
 *
 * @author Philippa Bather
 */
public class RegionNotFoundException extends RuntimeException{

    public RegionNotFoundException(String region) {
        super(region + REGION_NOT_FOUND_EXCEPTION_MSG);
    }
}
