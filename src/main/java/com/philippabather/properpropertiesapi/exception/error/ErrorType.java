package com.philippabather.properpropertiesapi.exception.error;

/**
 * ErrorType - enum para describe el tipo de error: el código y el estado/nombre
 *
 * @author Philippa Bather
 */
public enum ErrorType {
    JSON_PARSE_ERROR("400", "JSON PARSE ERROR"),
    CLIENT_NOT_FOUND("404", "CLIENT NOT FOUND"),
    PROPRIETOR_NOT_FOUND("404", "PROPRIETOR NOT FOUND"),
    INTERNAL_SERVER_ERROR("500", "INTERNAL SERVER ERROR"),
    NOT_ACCEPTABLE("406", "NOT ACCEPTABLE"),
    PROPERTY_NOT_FOUND("404", "PROPERTY NOT FOUND"),
    PROPERTY_STATUS_NOT_FOUND("404", "PROPERTY STATUS NOT FOUND"),
    PROPERTY_TYPE_NOT_FOUND("404", "PROPERTY TYPE NOT FOUND"),
    VALIDATION_UNPROCESSABLE_ENTITY("422", "VALIDATION");

    private String code;
    private String httpStatus;

    ErrorType(String code, String httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public String getCode() {
        return code;
    }

    public String getHttpStatus() {
        return httpStatus;
    }
}
