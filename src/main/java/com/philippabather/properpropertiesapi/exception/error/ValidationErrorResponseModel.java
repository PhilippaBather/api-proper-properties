package com.philippabather.properpropertiesapi.exception.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * ValidationErrorResponseModel - la clase define el modelo de respuesta de errores de validación
 *
 * @author Philippa Bather
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorResponseModel {
    private String type;
    private List<ValidationErrorModel> errors;
}
