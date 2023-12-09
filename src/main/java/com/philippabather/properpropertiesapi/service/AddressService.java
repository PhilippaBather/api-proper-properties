package com.philippabather.properpropertiesapi.service;

import com.philippabather.properpropertiesapi.dto.AddressDTOIn;
import com.philippabather.properpropertiesapi.dto.AddressDTOOut;
import com.philippabather.properpropertiesapi.exception.AddressNotFoundException;
import com.philippabather.properpropertiesapi.exception.RegionNotFoundException;

import java.util.Set;

/**
 * AddressService - la interfaz de servicio para manejar la entidad Address ('dirección').
 *
 * @author Philippa Bather
 */

public interface AddressService {

    Set<AddressDTOOut> getAll();
    Set<AddressDTOOut> getAllByPostcode(String postcode);
    Set<AddressDTOOut> getAllByRegion(String region) throws RegionNotFoundException;
    Set<AddressDTOOut> getAllByTown(String town);
    AddressDTOOut save(AddressDTOIn addressDTOIn);
    AddressDTOOut getById(long addressId) throws AddressNotFoundException;
    AddressDTOOut updateById(long addressId, AddressDTOIn addressDTOIn) throws AddressNotFoundException;
    void deleteById(long addressId) throws AddressNotFoundException;

}
