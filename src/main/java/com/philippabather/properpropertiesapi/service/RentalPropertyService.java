package com.philippabather.properpropertiesapi.service;

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

    Set<RentalProperty> findAll();
    Set<RentalProperty> findAllByMonthlyRent(BigDecimal rentPerMonth);
    Set<RentalProperty> findAllByMinTenancy(int minTenancy);
    Set<RentalProperty> findByNumBedrooms(int numBedrooms);
    RentalProperty save(long proprietorId, RentalProperty rentalPropertyDTOIn) throws ProprietorNotFoundException;
    RentalProperty findById(long propertyId) throws PropertyNotFoundException;
    RentalProperty updateById(long propertyId, RentalProperty rentalPropertyDTOIn) throws PropertyNotFoundException;
    void deleteById(long propertyId) throws PropertyNotFoundException;
    void deleteAddressById(long propertyId) throws PropertyNotFoundException;

}
