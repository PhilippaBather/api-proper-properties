package com.philippabather.properpropertiesapi.service;

import com.philippabather.properpropertiesapi.dto.AddressDTOIn;
import com.philippabather.properpropertiesapi.dto.AddressDTOOut;
import com.philippabather.properpropertiesapi.dto.PropertyDTOOut;
import com.philippabather.properpropertiesapi.exception.AddressNotFoundException;
import com.philippabather.properpropertiesapi.exception.RegionNotFoundException;
import com.philippabather.properpropertiesapi.model.Address;
import com.philippabather.properpropertiesapi.model.Property;
import com.philippabather.properpropertiesapi.model.Region;
import com.philippabather.properpropertiesapi.repository.AddressRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * AddressServiceImpl - implementa la interfaz de servicio AddressServoce; maneja información sobre la entidad Address ('dirección').
 *
 * @author Philippa Bather
 */
@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepo;

    private final ModelMapper modelMapper;

    public AddressServiceImpl(AddressRepository addressRepo, ModelMapper modelMapper) {
        this.addressRepo = addressRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public Set<AddressDTOOut> getAll() {
        Set<Address> addresses = addressRepo.getAll();
        return convertToAddressDTOOutSet(addresses);
    }

    @Override
    public Set<AddressDTOOut> getAllByPostcode(String postcode) {
        Set<Address> addresses = addressRepo.getAllByPostCode(postcode);
        return convertToAddressDTOOutSet(addresses);
    }

    @Override
    public Set<AddressDTOOut> getAllByRegion(String region) throws RegionNotFoundException {
        Region regionEnum = getRegionEnum(region);
        Set<Address> addresses = addressRepo.getAllByRegion(regionEnum);
        return convertToAddressDTOOutSet(addresses);
    }

    @Override
    public Set<AddressDTOOut> getAllByTown(String town) {
        Set<Address> addresses = addressRepo.getAllByTown(town);
        return convertToAddressDTOOutSet(addresses);
    }

    @Override
    public AddressDTOOut save(AddressDTOIn addressDTOIn) {
        Address address = new Address();
        modelMapper.map(addressDTOIn, address);

        Address savedAddress = addressRepo.save(address);

        AddressDTOOut addressDTOOut = new AddressDTOOut();
        modelMapper.map(savedAddress, addressDTOOut);

        return addressDTOOut;
    }

    @Override
    public AddressDTOOut getById(long addressId) throws AddressNotFoundException {
        Address address = addressRepo.findById(addressId).orElseThrow(() -> new AddressNotFoundException(addressId));
        AddressDTOOut addressDTOOut = new AddressDTOOut();
        modelMapper.map(address, addressDTOOut);
        return addressDTOOut;
    }

    @Override
    public AddressDTOOut updateById(long addressId, AddressDTOIn addressDTOIn) throws AddressNotFoundException {
        Address address = addressRepo.findById(addressId).orElseThrow(() -> new AddressNotFoundException(addressId));
        modelMapper.map(addressDTOIn, address);
        address.setId(addressId);

        Address updatedAddress = addressRepo.save(address);

        AddressDTOOut addressDTOOut = new AddressDTOOut();
        modelMapper.map(updatedAddress, addressDTOOut);
        return addressDTOOut;
    }

    @Override
    public void deleteById(long addressId) throws AddressNotFoundException {
        Address address = addressRepo.findById(addressId).orElseThrow(() -> new AddressNotFoundException(addressId));
        addressRepo.delete(address);
    }

    private Set<AddressDTOOut> convertToAddressDTOOutSet(Set<Address> addresses) {
        Set<AddressDTOOut> addressesDTOOuts = new HashSet<>();

        for (Address address:
                addresses) {
            AddressDTOOut addressDTOOut = new AddressDTOOut();
            modelMapper.map(address, addressDTOOut);
            addressesDTOOuts.add(addressDTOOut);
        }

        return addressesDTOOuts;
    }

    private Region getRegionEnum(String region) throws RegionNotFoundException {
        return switch(region.toUpperCase()) {
            case "ANDALUSIA" -> Region.ANDALUSIA;
            case "ARAGON" -> Region.ARAGON;
            case "ASTURIAS" -> Region.ASTURIAS;
            case "BALEARIC ISLANDS" -> Region.BALEARIC_ISLANDS;
            case "BASQUE COUNTRY" -> Region.BASQUE_COUNTRY;
            case "CANARY ISLANDS" -> Region.CANARY_ISLANDS;
            case "CANTABRIA" -> Region.CANTABRIA;
            case "CASTILE AND LEON" -> Region.CASTILE_AND_LEON;
            case "CASTILE LA MANCHA" -> Region.CASTILE_LA_MANCHA;
            case "CATALONIA" -> Region.CATALONIA;
            case "CEUTA" -> Region.CEUTA;
            case "EXTREMADURA" -> Region.EXTREMADURA;
            case "GALICIA" -> Region.GALICIA;
            case "LA RIOJA" -> Region.LA_RIOJA;
            case "MADRID" -> Region.MADRID;
            case "MURCIA" -> Region.MURCIA;
            case "MELILLA" -> Region.MELILLA;
            case "NAVARRA" -> Region.NAVARRA;
            case "VALENCIA" -> Region.VALENCIA;
            default -> throw new RegionNotFoundException(region);
        };
    }
}
