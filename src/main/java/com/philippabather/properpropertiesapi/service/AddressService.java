package com.philippabather.properpropertiesapi.service;

import com.philippabather.properpropertiesapi.dto.AddressDTOIn;
import com.philippabather.properpropertiesapi.dto.AddressDTOOut;
import com.philippabather.properpropertiesapi.exception.AddressNotFoundException;
import com.philippabather.properpropertiesapi.exception.PropertyNotFoundException;
import com.philippabather.properpropertiesapi.exception.RegionNotFoundException;

import java.util.Set;

/**
 * AddressService - la interfaz de servicio para manejar la entidad Address ('dirección').
 *
 * @author Philippa Bather
 */
public interface AddressService {

    Set<AddressDTOOut> findAll();
    Set<AddressDTOOut> findAllByPostcode(String postcode);
    Set<AddressDTOOut> findAllByRegion(String region) throws RegionNotFoundException;
    Set<AddressDTOOut> findAllByTown(String town);
    AddressDTOOut saveRentalAddress(long propertyId, AddressDTOIn addressDTOIn) throws PropertyNotFoundException;
    AddressDTOOut saveSaleAddress(long propertyId, AddressDTOIn addressDTOIn) throws PropertyNotFoundException;
    AddressDTOOut getById(long addressId) throws AddressNotFoundException;
    AddressDTOOut updateByRentalPropertyId(long propertyId, AddressDTOIn addressDTOIn) throws AddressNotFoundException, PropertyNotFoundException;
    AddressDTOOut updateBySalePropertyId(long propertyId, AddressDTOIn addressDTOIn) throws AddressNotFoundException, PropertyNotFoundException;
    void deleteAddressByRentalPropertyId(long propertyId) throws AddressNotFoundException, PropertyNotFoundException;
    void deleteAddressBySalePropertyId(long propertyId) throws AddressNotFoundException, PropertyNotFoundException;
}
