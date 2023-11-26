package com.philippabather.properpropertiesapi.exception.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ValidationErrorModel - la clase define el error de validación
 *
 * @author Philippa Bather
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorModel {
    private String constraint;
    private String detail;
    private String mapping;
}
