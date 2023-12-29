package com.philippabather.properpropertiesapi.service;

import com.philippabather.properpropertiesapi.dto.AddressDTOIn;
import com.philippabather.properpropertiesapi.dto.AddressDTOOut;
import com.philippabather.properpropertiesapi.exception.AddressNotFoundException;
import com.philippabather.properpropertiesapi.exception.PropertyNotFoundException;
import com.philippabather.properpropertiesapi.exception.RegionNotFoundException;
import com.philippabather.properpropertiesapi.model.Address;
import com.philippabather.properpropertiesapi.model.Region;
import com.philippabather.properpropertiesapi.model.RentalProperty;
import com.philippabather.properpropertiesapi.model.SaleProperty;
import com.philippabather.properpropertiesapi.repository.AddressRepository;
import com.philippabather.properpropertiesapi.repository.RentalPropertyRepository;
import com.philippabather.properpropertiesapi.repository.SalePropertyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * AddressServiceImpl - implementa la interfaz de servicio AddressService; maneja información sobre la entidad Address ('dirección').
 *
 * @author Philippa Bather
 */
@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepo;
    private final RentalPropertyRepository rentalRepo;
    private final RentalPropertyService rentalService;
    private final SalePropertyService saleService;
    private final SalePropertyRepository saleRepo;
    private final ModelMapper modelMapper;

    public AddressServiceImpl(AddressRepository addressRepo, RentalPropertyRepository rentalRepo,
                              RentalPropertyService rentalService, SalePropertyService saleService,
                              SalePropertyRepository saleRepo, ModelMapper modelMapper) {
        this.addressRepo = addressRepo;
        this.rentalRepo = rentalRepo;
        this.rentalService = rentalService;
        this.saleService = saleService;
        this.saleRepo = saleRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public Set<AddressDTOOut> findAll() {
        Set<Address> addresses = addressRepo.findAll();
        return convertToAddressDTOOutSet(addresses);
    }

    @Override
    public Set<AddressDTOOut> findAllByPostcode(String postcode) {
        Set<Address> addresses = addressRepo.findAllByPostCode(postcode);
        return convertToAddressDTOOutSet(addresses);
    }

    @Override
    public Set<AddressDTOOut> findAllByRegion(String region) throws RegionNotFoundException {
        Region regionEnum = getRegionEnum(region);
        Set<Address> addresses = addressRepo.findAllByRegion(regionEnum);
        return convertToAddressDTOOutSet(addresses);
    }

    @Override
    public Set<AddressDTOOut> findAllByTown(String town) {
        Set<Address> addresses = addressRepo.findAllByTown(town);
        return convertToAddressDTOOutSet(addresses);
    }

    @Override
    public AddressDTOOut saveRentalAddress(long propertyId, AddressDTOIn addressDTOIn) {
        RentalProperty rentalProperty = rentalRepo.findById(propertyId).orElseThrow(() -> new PropertyNotFoundException(propertyId));

        Address address = addressInMapping(addressDTOIn);
        Address savedAddress = addressRepo.save(address);

        rentalProperty.setAddress(savedAddress);
        rentalRepo.save(rentalProperty);

        return addressOutMapping(savedAddress);
    }

    @Override
    public AddressDTOOut saveSaleAddress(long propertyId, AddressDTOIn addressDTOIn) throws PropertyNotFoundException {
        SaleProperty saleProperty = saleRepo.findById(propertyId).orElseThrow(() -> new PropertyNotFoundException(propertyId));

        Address address = addressInMapping(addressDTOIn);
        Address savedAddress = addressRepo.save(address);

        saleProperty.setAddress(savedAddress);
        saleRepo.save(saleProperty);

        return addressOutMapping(savedAddress);
    }

    @Override
    public AddressDTOOut getById(long addressId) throws AddressNotFoundException {
        Address address = addressRepo.findById(addressId).orElseThrow(() -> new AddressNotFoundException(addressId));
        return addressOutMapping(address);
    }

    @Override
    public AddressDTOOut updateByRentalPropertyId(long propertyId, AddressDTOIn addressDTOIn) throws AddressNotFoundException {
        RentalProperty property = rentalRepo.findById(propertyId).orElseThrow(() -> new PropertyNotFoundException(propertyId));
        long addressId = property.getAddress().getId();

        Address address = addressInMapping(addressDTOIn);
        address.setId(addressId);

        Address updatedAddress = addressRepo.save(address);
        property.setAddress(updatedAddress);
        rentalRepo.save(property);

        return addressOutMapping(updatedAddress);
    }

    @Override
    public AddressDTOOut updateBySalePropertyId(long propertyId, AddressDTOIn addressDTOIn) throws AddressNotFoundException,
            PropertyNotFoundException {
        SaleProperty property = saleRepo.findById(propertyId).orElseThrow(() -> new PropertyNotFoundException(propertyId));
        long addressId = property.getAddress().getId();

        Address address = addressInMapping(addressDTOIn);
        address.setId(addressId);

        Address updatedAddress = addressRepo.save(address);
        property.setAddress(updatedAddress);
        saleRepo.save(property);

        return addressOutMapping(updatedAddress);
    }

    @Override
    public void deleteAddressByRentalPropertyId(long propertyId) throws AddressNotFoundException, PropertyNotFoundException {
        RentalProperty property = rentalRepo.findById(propertyId).orElseThrow(() -> new PropertyNotFoundException(propertyId));
        long addressId = property.getAddress().getId();
        rentalService.deleteAddressById(propertyId);
        Address address = addressRepo.findById(addressId).orElseThrow(() -> new AddressNotFoundException(addressId));
        addressRepo.delete(address);
    }

    @Override
    public void deleteAddressBySalePropertyId(long propertyId) throws AddressNotFoundException, PropertyNotFoundException {
        SaleProperty property = saleRepo.findById(propertyId).orElseThrow(() -> new PropertyNotFoundException(propertyId));
        long addressId = property.getAddress().getId();
        saleService.deleteAddressById(propertyId);
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

    private Address addressInMapping(AddressDTOIn addressDTOIn) {
        Address address = new Address();
        modelMapper.map(addressDTOIn, address);
        return address;
    }

    private AddressDTOOut addressOutMapping(Address address) {
        AddressDTOOut addressDTOOut = new AddressDTOOut();
        modelMapper.map(address, addressDTOOut);
        return addressDTOOut;
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
