package com.philippabather.properpropertiesapi.service;

import com.philippabather.properpropertiesapi.dto.PropertyDTOIn;
import com.philippabather.properpropertiesapi.dto.PropertyDTOOut;
import com.philippabather.properpropertiesapi.exception.PropertyNotFoundException;
import com.philippabather.properpropertiesapi.exception.ProprietorNotFoundException;

import java.util.Set;

/**
 * PropertyService - la interfaz de servicio para manejar la entidad Property ('inmueble').
 *
 * @author Philippa Bather
 */
public interface PropertyService {

    // TODO - change list to SET
    Set<PropertyDTOOut> findAll();
    Set<PropertyDTOOut> findAllByPropertyStatus(String propertyStatus);
    Set<PropertyDTOOut> findAllByPropertyType(String propertyType);
    Set<PropertyDTOOut> findAllByNumBedrooms(int numBedrooms);
    PropertyDTOOut save (long proprietorId, PropertyDTOIn property) throws ProprietorNotFoundException;
    PropertyDTOOut getById(long propertyId) throws PropertyNotFoundException;
    PropertyDTOOut updateById(long propertyId, PropertyDTOIn propertyDTOIn) throws PropertyNotFoundException;
    void deleteById(long propertyId) throws PropertyNotFoundException;
}
