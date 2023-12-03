package com.philippabather.properpropertiesapi.exception;

import static com.philippabather.properpropertiesapi.constants.ErrorMessages.PROPRIETOR_NOT_FOUND_EXCEPTION_MSG;

public class ProprietorNotFoundException extends RuntimeException {

    public ProprietorNotFoundException(long proprietorId) {
        super(PROPRIETOR_NOT_FOUND_EXCEPTION_MSG + proprietorId);
    }
}
