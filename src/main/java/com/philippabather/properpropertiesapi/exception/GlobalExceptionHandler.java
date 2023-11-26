package com.philippabather.properpropertiesapi.exception;

import com.philippabather.properpropertiesapi.constants.ErrorMessages;
import com.philippabather.properpropertiesapi.exception.error.ErrorType;
import com.philippabather.properpropertiesapi.exception.error.Response;
import com.philippabather.properpropertiesapi.exception.error.ValidationErrorModel;
import com.philippabather.properpropertiesapi.exception.error.ValidationErrorResponseModel;
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

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY) // 422
    public ValidationErrorResponseModel handleException(MethodArgumentNotValidException manve) {
        List<ValidationErrorModel> errors = processValidationErrors(manve);

        return ValidationErrorResponseModel
                .builder()
                .errors(errors)
                .type(ErrorType.VALIDATION_UNPROCESSABLE_ENTITY.getHttpStatus())
                .build();
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE) // 406 || 400 (Parse Error)
    public Response handleException(HttpMessageNotReadableException hmnre) {
        if(checkForParseError(hmnre)) {
            return new Response(ErrorType.JSON_PARSE_ERROR.getCode(), ErrorType.JSON_PARSE_ERROR.getHttpStatus(), ErrorMessages.JSON_PARSE_ERROR);
        }
        return new Response(ErrorType.NOT_ACCEPTABLE.getCode(), ErrorType.NOT_ACCEPTABLE.getHttpStatus(), hmnre.getMessage());
    }

    @ExceptionHandler(value = HttpServerErrorException.InternalServerError.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
    public Response handleException(HttpServerErrorException.InternalServerError ise) {
        return new Response(ErrorType.INTERNAL_SERVER_ERROR.getCode(), ErrorType.INTERNAL_SERVER_ERROR.getHttpStatus(), ise.getMessage());
    }

    // custom exceptions
    @ExceptionHandler(value = ClientNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND) // 404
    public Response handleException(ClientNotFoundException cnfe) {
        return new Response(ErrorType.CLIENT_NOT_FOUND.getCode(), ErrorType.CLIENT_NOT_FOUND.getHttpStatus(), cnfe.getMessage());
    }

    // helper methods
    private List<ValidationErrorModel> processValidationErrors(MethodArgumentNotValidException manve) {
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
        return validationErrors;
    }

    private boolean checkForParseError(HttpMessageNotReadableException hmnre) {
        return hmnre.getMessage().contains("JSON parse error");
    }
}
