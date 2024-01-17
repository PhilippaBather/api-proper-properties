package com.philippabather.properpropertiesapi.dto;

import com.philippabather.properpropertiesapi.model.PropertyStatus;
import com.philippabather.properpropertiesapi.model.PropertyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * PropertyDTOOut - El objeto de la transferencia de datos (DTO) de la clase Property ('inmueble') al cliente/consumo de API.
 *
 * @author Philippa Bather
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyDTOOut {

    private long id;

    private PropertyStatus propertyStatus;

    private PropertyType propertyType;

    private double latitude;

    private double longitude;

    private int metresSqr;

    private String description;

    private LocalDate availableFrom; // disponibleDesde

    private int numBedrooms;

    private int numBathrooms;

    private boolean isLift;

    private boolean isParking;

    private ProprietorDTOOut proprietor;
}
