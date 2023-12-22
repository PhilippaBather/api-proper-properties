package com.philippabather.properpropertiesapi.dto;

import com.philippabather.properpropertiesapi.model.PropertyStatus;
import com.philippabather.properpropertiesapi.model.PropertyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleDTOOut {
    private PropertyStatus propertyStatus;
    private PropertyType propertyType;
    private double latitude;
    private double longitude;
    private int metresSqr;
    private String description;
    private LocalDate availableFrom; // disponibleDesde
    private int numBedrooms;
    private int numBathrooms;
    private boolean isParking;
    private boolean isLift; // ¿hay acensor?
    private long id;
    private BigDecimal price;
    private boolean isLeasehold;
    private LocalDate constructionDate;
}
