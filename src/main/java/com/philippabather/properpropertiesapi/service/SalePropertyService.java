package com.philippabather.properpropertiesapi.service;

import com.philippabather.properpropertiesapi.exception.PropertyNotFoundException;
import com.philippabather.properpropertiesapi.exception.ProprietorNotFoundException;
import com.philippabather.properpropertiesapi.model.SaleProperty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public interface SalePropertyService {

    Set<SaleProperty> findAll();
    Set<SaleProperty> findAllByPrice(BigDecimal price);
    Set<SaleProperty> findAllByConstructionDate(LocalDate constructionDate);
    Set<SaleProperty> findAllByIsLeasehold(boolean isLeasehold);
    SaleProperty save(long proprietorId, SaleProperty saleProperty) throws ProprietorNotFoundException;
    SaleProperty findById(long propertyId) throws PropertyNotFoundException;
    SaleProperty updateById(long propertyId, SaleProperty saleProperty) throws PropertyNotFoundException;
    void deleteById(long propertyId) throws PropertyNotFoundException;
    void deleteAddressById(long propertyId) throws PropertyNotFoundException;
}
