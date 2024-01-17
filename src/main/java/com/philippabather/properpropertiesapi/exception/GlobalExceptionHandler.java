package com.philippabather.properpropertiesapi.exception;

import com.philippabather.properpropertiesapi.constants.ErrorMessages;
import com.philippabather.properpropertiesapi.exception.error.ErrorType;
import com.philippabather.properpropertiesapi.exception.error.Response;
import com.philippabather.properpropertiesapi.exception.error.ValidationErrorModel;
import com.philippabather.properpropertiesapi.exception.error.ValidationErrorResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import java.util.ArrayList;
import java.util.List;

/**
 * GlobalExceptionHandler - maneja de forma global las respuestas de errores
 *
 * @author Philippa Bather
 */

@Configuration
@RestController
@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE) // 406 || 400 (Parse Error)
    public Response handleException(HttpMessageNotReadableException hmnre) {
        logger.info("GlobalExceptionHandler_handleException: NOT_ACCEPTABLE");
        if(checkForParseError(hmnre)) {
            return new Response(ErrorType.JSON_PARSE_ERROR.getCode(), ErrorType.JSON_PARSE_ERROR.getHttpStatus(), ErrorMessages.JSON_PARSE_ERROR);
        }
        return new Response(ErrorType.NOT_ACCEPTABLE.getCode(), ErrorType.NOT_ACCEPTABLE.getHttpStatus(), hmnre.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY) // 422
    public ValidationErrorResponseModel handleException(MethodArgumentNotValidException manve) {
        logger.info("GlobalExceptionHandler_handleException: UNPROCESSABLE_ENTITY");
        List<ValidationErrorModel> errors = processValidationErrors(manve);
        return ValidationErrorResponseModel
                .builder()
                .errors(errors)
                .type(ErrorType.VALIDATION_UNPROCESSABLE_ENTITY.getHttpStatus())
                .build();
    }

    @ExceptionHandler(value = HttpServerErrorException.InternalServerError.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
    public Response handleException(HttpServerErrorException.InternalServerError ise) {
        logger.info("GlobalExceptionHandler_handleException: INTERNAL_SERVER_ERROR");
        return new Response(ErrorType.INTERNAL_SERVER_ERROR.getCode(), ErrorType.INTERNAL_SERVER_ERROR.getHttpStatus(), ise.getMessage());
    }


    // custom exceptions

    @ExceptionHandler(value = AddressNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND) // 404
    public Response handleException(AddressNotFoundException anfe) {
        logger.info("GlobalExceptionHandler_handleException: Custom Exception: ADDRESS_NOT_FOUND");
        return new Response(ErrorType.ADDRESS_NOT_FOUND.getCode(), ErrorType.ADDRESS_NOT_FOUND.getHttpStatus(), anfe.getMessage());
    }

    @ExceptionHandler(value = ClientNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND) // 404
    public Response handleException(ClientNotFoundException cnfe) {
        logger.info("GlobalExceptionHandler_handleException: Custom Exception: CLIENT_NOT_FOUND");
        return new Response(ErrorType.CLIENT_NOT_FOUND.getCode(), ErrorType.CLIENT_NOT_FOUND.getHttpStatus(), cnfe.getMessage());
    }

    @ExceptionHandler(value = InvalidLoginException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response handleException(InvalidLoginException ile) {
        logger.info("GlobalExceptionHandler_handleException: Custom Exception: INVALID_LOGIN");
        return new Response(ErrorType.INVALID_LOGIN.getCode(), ErrorType.INVALID_LOGIN.getHttpStatus(), ile.getMessage());
    }

    @ExceptionHandler(value = ProprietorNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND) // 404
    public Response handleException(ProprietorNotFoundException pnfe){
        logger.info("GlobalExceptionHandler_handleException: Custom Exception: PROPRIETOR_NOT_FOUND");
        return new Response(ErrorType.PROPRIETOR_NOT_FOUND.getCode(), ErrorType.PROPRIETOR_NOT_FOUND.getHttpStatus(), pnfe.getMessage());
    }


    @ExceptionHandler(value = PropertyNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response handleException(PropertyNotFoundException pnfe) {
        logger.info("GlobalExceptionHandler_handleException: Custom Exception: PROPERTY_NOT_FOUND");
        return new Response(ErrorType.PROPERTY_NOT_FOUND.getCode(), ErrorType.PROPERTY_NOT_FOUND.getHttpStatus(), pnfe.getMessage());
    }

    @ExceptionHandler(value = PropertyStatusNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response handleException(PropertyStatusNotFoundException psnfe) {
        logger.info("GlobalExceptionHandler_handleException: Custom Exception: PROPERTY_STATUS_NOT_FOUND");
        return new Response(ErrorType.PROPERTY_STATUS_NOT_FOUND.getCode(), ErrorType.PROPERTY_STATUS_NOT_FOUND.getHttpStatus(), psnfe.getMessage());
    }

    @ExceptionHandler(value = PropertyTypeNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response handleException(PropertyTypeNotFoundException ptnfe) {
        logger.info("GlobalExceptionHandler_handleException: Custom Exception: PROPERTY_TYPE_NOT_FOUND");
        return new Response(ErrorType.PROPERTY_TYPE_NOT_FOUND.getCode(), ErrorType.PROPERTY_TYPE_NOT_FOUND.getHttpStatus(), ptnfe.getMessage());
    }

    @ExceptionHandler(value = RegistrationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT) // 409
    public Response handleException(RegistrationException re) {
        logger.info("GlobalExceptionHandler_handleException: Custom Exception: REGISTRATION_USERNAME_EXISTS");
        return new Response(ErrorType.REGISTRATION_USERNAME_EXISTS.getCode(), ErrorType.REGISTRATION_USERNAME_EXISTS.getHttpStatus(), re.getMessage());
    }


    // helper methods
    private List<ValidationErrorModel> processValidationErrors(MethodArgumentNotValidException manve) {
        logger.info("start: GlobalExceptionHandler_processValidationErrors");
        List<ValidationErrorModel> validationErrors = new ArrayList<>();
        for (FieldError fieldError : manve.getBindingResult().getFieldErrors()) {
            ValidationErrorModel validationErrorModel = ValidationErrorModel
                    .builder()
                    .constraint(fieldError.getCode())
                    .mapping(fieldError.getObjectName() + "/" + fieldError.getField())
                    .detail(fieldError.getField() + ": " + fieldError.getDefaultMessage())
                    .build();
            validationErrors.add(validationErrorModel);
        }
        logger.info("end: GlobalExceptionHandler_processValidationErrors");
        return validationErrors;
    }

    private boolean checkForParseError(HttpMessageNotReadableException hmnre) {
        logger.info("GlobalExceptionHandler_handleException: checkForParseError");
        return hmnre.getMessage().contains("JSON parse error");
    }
}
