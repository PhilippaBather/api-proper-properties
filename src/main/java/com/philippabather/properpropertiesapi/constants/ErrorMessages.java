package com.philippabather.properpropertiesapi.constants;

/**
 * ErrorMessages - constantes para mensajes de errores
 *
 * @author Philippa Bather
 */
public class ErrorMessages {
    public static final String ADDRESS_NOT_FOUND_EXCEPTION_MSG = "Address not found with ID: ";
    public static final String CLIENT_NOT_FOUND_EXCEPTION_MSG = "Client not found with ID: ";
    public static final String LOGIN_INVALID = "Invalid login for username: ";
    public static final String PROPRIETOR_NOT_FOUND_EXCEPTION_MSG = "Proprietor not found with ID: ";
    public static final String PROPRIETOR_USERNAME_NOT_FOUND_EXCEPTION_MSG = "Proprietor not found with username: ";
    public static final String PROPERTY_NOT_FOUND_EXCEPTION_MSG = "Property not found with ID: ";
    public static final String PROPERTY_STATUS_NOT_FOUND_EXCEPTION_MSG = ": property status not found; valid statuses are RENTAL or SALE";
    public static final String PROPERTY_TYPE_NOT_FOUND_EXCEPTION_MSG = ": property type not found; valid types are: COMMERCIAL, FLAT, or HOUSE";
    public static final String REGION_NOT_FOUND_EXCEPTION_MSG = ": region not found.";
    public static final String REGISTRATION_EXCEPTION_USERNAME_EXISTS_MSG = "Registration Exception: Username Exists -  ";
    public static final String UNAUTHORIZED_EXCEPTION = "Invalid credentials: valid token required";
    public static final String JSON_PARSE_ERROR = "JSON parse error: check correct value types provided for all fields";

}
