package com.philippabather.properpropertiesapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.philippabather.properpropertiesapi.model.Address;
import com.philippabather.properpropertiesapi.model.PropertyStatus;
import com.philippabather.properpropertiesapi.model.PropertyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * RentalDTOOut - El objeto de la transferencia de datos (DTO) de la clase RentalProperty al cliente/consumo de API..
 *
 * @author Philippa Bather
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalDTOOut {

    private PropertyStatus propertyStatus;
    private PropertyType propertyType;
    private double latitude;
    private double longitude;
    private int metresSqr;
    private String description;
    private LocalDate availableFrom; // disponibleDesde
    private int numBedrooms;
    private int numBathrooms;
    @JsonProperty
    private boolean isParking;
    @JsonProperty
    private boolean isLift; // ¿hay acensor?
    private long id;
    private BigDecimal rentPerMonth;
    private BigDecimal deposit;
    private int minTenancy;
    @JsonProperty
    private boolean isFurnished;
    @JsonProperty
    private boolean isPetFriendly;

    private Address address;

}
