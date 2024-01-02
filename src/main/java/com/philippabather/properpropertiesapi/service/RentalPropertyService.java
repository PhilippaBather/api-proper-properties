package com.philippabather.properpropertiesapi.service;

import com.philippabather.properpropertiesapi.dto.RentalDTOOut;
import com.philippabather.properpropertiesapi.exception.PropertyNotFoundException;
import com.philippabather.properpropertiesapi.exception.ProprietorNotFoundException;
import com.philippabather.properpropertiesapi.model.RentalProperty;

import java.math.BigDecimal;
import java.util.Set;

/**
 * RentalPropertyService - la interfaz de servicio para manejar la entidad RentalProperty ('alquiler').
 *
 * @author Philippa Bather
 */
public interface RentalPropertyService {

    Set<RentalDTOOut> findAll();
    Set<RentalDTOOut> findAllByMonthlyRent(BigDecimal rentPerMonth);
    Set<RentalDTOOut> findAllByMinTenancy(int minTenancy);
    Set<RentalDTOOut> findByNumBedrooms(int numBedrooms);
    RentalDTOOut save(long proprietorId, RentalProperty rentalProperty) throws ProprietorNotFoundException;
    RentalDTOOut findById(long propertyId) throws PropertyNotFoundException;
    RentalDTOOut updateById(long propertyId, RentalProperty rentalProperty) throws PropertyNotFoundException;
    void deleteById(long propertyId) throws PropertyNotFoundException;
    void deleteAddressById(long propertyId) throws PropertyNotFoundException;

}
