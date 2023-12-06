package com.philippabather.properpropertiesapi.dto;

import com.philippabather.properpropertiesapi.constants.ValidationMessages;
import com.philippabather.properpropertiesapi.model.PropertyStatus;
import com.philippabather.properpropertiesapi.model.PropertyType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import static com.philippabather.properpropertiesapi.constants.Constants.*;
import static com.philippabather.properpropertiesapi.constants.ValidationMessages.*;

/**
 * PropertyDTOIn - Data Transfer Object (DTO) para la clase 'Property' (inmueble).
 *
 * @author Philippa Bather
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyDTOIn {

    private PropertyStatus propertyStatus;

    private PropertyType propertyType;

    @Min(value = LATITUDE_MIN, message = ValidationMessages.VALIDATION_LATITUDE_REQUIRED)
    @Max(value = LATITUDE_MAX, message = ValidationMessages.VALIDATION_LATITUDE_REQUIRED)
    private double latitude;

    @Min(value = LONGITUDE_MIN, message = ValidationMessages.VALIDATION_LONGITUDE_REQUIRED)
    @Max(value = LONGITUDE_MAX, message = ValidationMessages.VALIDATION_LONGITUDE_REQUIRED)
    private double longitude;

    private int metresSqr;

    private String description;

    @NotNull(message = VALIDATION_DATE)
    @DateTimeFormat
    private LocalDate availableFrom; // disponibleDesde

    @Min(value = 0, message = VALIDATION_NUM_BEDROOMS)
    private int numBedrooms;

    @Min(value = 0, message = VALIDATION_NUM_BEDROOMS)
    private int numBathrooms;

    @NotNull(message= ValidationMessages.VALIDATION_BOOLEAN_REQUIRED)
    private boolean isLift;

    @NotNull(message = VALIDATION_BOOLEAN_REQUIRED)
    private boolean isParking;

}
