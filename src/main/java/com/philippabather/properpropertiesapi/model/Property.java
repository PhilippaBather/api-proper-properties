package com.philippabather.properpropertiesapi.model;

import com.philippabather.properpropertiesapi.constants.ValidationMessages;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
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
 * Property - un inmueble
 *
 * @author Philippa Bather
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class Property {

    @Column
    private PropertyStatus propertyStatus;

    @Column
    private PropertyType propertyType;

    @Min(value = LATITUDE_MIN, message = VALIDATION_LATITUDE_REQUIRED)
    @Max(value = LATITUDE_MAX, message = VALIDATION_LATITUDE_REQUIRED)
    @Column
    private double latitude;

    @Min(value = LONGITUDE_MIN, message = VALIDATION_LONGITUDE_REQUIRED)
    @Max(value = LONGITUDE_MAX, message = VALIDATION_LONGITUDE_REQUIRED)
    @Column
    private double longitude;

    @Min(10)
    @Column
    private int metresSqr;

    @Column
    private String description;

    @NotNull(message = VALIDATION_DATE)
    @DateTimeFormat
    @Column
    private LocalDate availableFrom; // disponibleDesde

    @Min(value = 0, message = VALIDATION_NUM_BEDROOMS)
    @Column
    private int numBedrooms;

    @Min(value = 0, message = VALIDATION_NUM_BATHROOMS)
    @Column
    private int numBathrooms;

    @NotNull(message= ValidationMessages.VALIDATION_BOOLEAN_REQUIRED)
    @Column(name = "is_parking")
    private boolean isParking;

    @NotNull(message= ValidationMessages.VALIDATION_BOOLEAN_REQUIRED)
    @Column(name = "is_lift")
    private boolean isLift; // ¿hay acensor?

}
