package com.philippabather.properpropertiesapi.service;

import com.philippabather.properpropertiesapi.dto.SaleDTOOut;
import com.philippabather.properpropertiesapi.exception.PropertyNotFoundException;
import com.philippabather.properpropertiesapi.exception.ProprietorNotFoundException;
import com.philippabather.properpropertiesapi.model.SaleProperty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public interface SalePropertyService {

    Set<SaleDTOOut> findAll();
    Set<SaleDTOOut> findAllByPrice(BigDecimal price);
    Set<SaleDTOOut> findAllByConstructionDate(LocalDate constructionDate);
    Set<SaleDTOOut> findAllByMetresSqr(int metresSqr);
    SaleDTOOut save(long proprietorId, SaleProperty saleProperty) throws ProprietorNotFoundException;
    SaleDTOOut findById(long propertyId) throws PropertyNotFoundException;
    SaleDTOOut updateById(long propertyId, SaleProperty saleProperty) throws PropertyNotFoundException;
    void deleteById(long propertyId) throws PropertyNotFoundException;
    void deleteAddressById(long propertyId) throws PropertyNotFoundException;
}
