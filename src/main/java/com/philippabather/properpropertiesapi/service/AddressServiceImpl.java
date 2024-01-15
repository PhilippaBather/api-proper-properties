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
import com.philippabather.properpropertiesapi.security.ProprietorUserDetailsService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(AddressServiceImpl.class);
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
        logger.info("start: AddressServiceImpl_findAll");
        Set<Address> addresses = addressRepo.findAll();
        logger.info("end: AddressServiceImpl_findAll");
        return convertToAddressDTOOutSet(addresses);
    }

    @Override
    public Set<AddressDTOOut> findAllByPostcode(String postcode) {
        logger.info("start: AddressServiceImpl_findAllByPostcode");
        Set<Address> addresses = addressRepo.findAllByPostCode(postcode);
        logger.info("end: AddressServiceImpl_findAllByPostcode");
        return convertToAddressDTOOutSet(addresses);
    }

    @Override
    public Set<AddressDTOOut> findAllByRegion(String region) throws RegionNotFoundException {
        logger.info("start: AddressServiceImpl_findAllByRegion");
        Region regionEnum = getRegionEnum(region);
        Set<Address> addresses = addressRepo.findAllByRegion(regionEnum);
        logger.info("end: AddressServiceImpl_findAllByRegion");
        return convertToAddressDTOOutSet(addresses);
    }

    @Override
    public Set<AddressDTOOut> findAllByTown(String town) {
        logger.info("start: AddressServiceImpl_findAllByTown");
        Set<Address> addresses = addressRepo.findAllByTown(town);
        logger.info("end: AddressServiceImpl_findAllByTown");
        return convertToAddressDTOOutSet(addresses);
    }

    @Override
    public AddressDTOOut saveRentalAddress(long propertyId, AddressDTOIn addressDTOIn) {
        logger.info("start: AddressServiceImpl_saveRentalAddress");
        RentalProperty rentalProperty = rentalRepo.findById(propertyId).orElseThrow(() -> new PropertyNotFoundException(propertyId));

        Address address = addressInMapping(addressDTOIn);
        Address savedAddress = addressRepo.save(address);

        rentalProperty.setAddress(savedAddress);
        rentalRepo.save(rentalProperty);

        logger.info("end: AddressServiceImpl_saveRentalAddress");
        return addressOutMapping(savedAddress);
    }

    @Override
    public AddressDTOOut saveSaleAddress(long propertyId, AddressDTOIn addressDTOIn) throws PropertyNotFoundException {
        logger.info("start: AddressServiceImpl_saveSaleAddress");
        SaleProperty saleProperty = saleRepo.findById(propertyId).orElseThrow(() -> new PropertyNotFoundException(propertyId));

        Address address = addressInMapping(addressDTOIn);
        Address savedAddress = addressRepo.save(address);

        saleProperty.setAddress(savedAddress);
        saleRepo.save(saleProperty);

        logger.info("end: AddressServiceImpl_saveSaleAddress");
        return addressOutMapping(savedAddress);
    }

    @Override
    public AddressDTOOut getById(long addressId) throws AddressNotFoundException {
        logger.info("start: AddressServiceImpl_getById");
        Address address = addressRepo.findById(addressId).orElseThrow(() -> new AddressNotFoundException(addressId));
        logger.info("end: AddressServiceImpl_getById");
        return addressOutMapping(address);
    }

    @Override
    public AddressDTOOut updateByRentalPropertyId(long propertyId, AddressDTOIn addressDTOIn) throws AddressNotFoundException {
        logger.info("start: AddressServiceImpl_updateByRentalPropertyId");
        RentalProperty property = rentalRepo.findById(propertyId).orElseThrow(() -> new PropertyNotFoundException(propertyId));
        long addressId = property.getAddress().getId();

        Address address = addressInMapping(addressDTOIn);
        address.setId(addressId);

        Address updatedAddress = addressRepo.save(address);
        property.setAddress(updatedAddress);
        rentalRepo.save(property);

        logger.info("end: AddressServiceImpl_updateByRentalPropertyId");
        return addressOutMapping(updatedAddress);
    }

    @Override
    public AddressDTOOut updateBySalePropertyId(long propertyId, AddressDTOIn addressDTOIn) throws AddressNotFoundException,
            PropertyNotFoundException {
        logger.info("start: AddressServiceImpl_updateBySalePropertyId");
        SaleProperty property = saleRepo.findById(propertyId).orElseThrow(() -> new PropertyNotFoundException(propertyId));
        long addressId = property.getAddress().getId();

        Address address = addressInMapping(addressDTOIn);
        address.setId(addressId);

        Address updatedAddress = addressRepo.save(address);
        property.setAddress(updatedAddress);
        saleRepo.save(property);

        logger.info("end: AddressServiceImpl_updateBySalePropertyId");
        return addressOutMapping(updatedAddress);
    }

    @Override
    public void deleteAddressByRentalPropertyId(long propertyId) throws AddressNotFoundException, PropertyNotFoundException {
        logger.info("start: AddressServiceImpl_deleteAddressByRentalPropertyId");
        RentalProperty property = rentalRepo.findById(propertyId).orElseThrow(() -> new PropertyNotFoundException(propertyId));
        long addressId = property.getAddress().getId();
        rentalService.deleteAddressById(propertyId);
        Address address = addressRepo.findById(addressId).orElseThrow(() -> new AddressNotFoundException(addressId));
        addressRepo.delete(address);
        logger.info("end: AddressServiceImpl_deleteAddressByRentalPropertyId");
    }

    @Override
    public void deleteAddressBySalePropertyId(long propertyId) throws AddressNotFoundException, PropertyNotFoundException {
        logger.info("start: AddressServiceImpl_deleteAddressBySalePropertyId");
        SaleProperty property = saleRepo.findById(propertyId).orElseThrow(() -> new PropertyNotFoundException(propertyId));
        long addressId = property.getAddress().getId();
        saleService.deleteAddressById(propertyId);
        Address address = addressRepo.findById(addressId).orElseThrow(() -> new AddressNotFoundException(addressId));
        addressRepo.delete(address);
        logger.info("end: AddressServiceImpl_deleteAddressBySalePropertyId");
    }

    private Set<AddressDTOOut> convertToAddressDTOOutSet(Set<Address> addresses) {
        logger.info("start: AddressServiceImpl_convertToAddressDTOOutSet");
        Set<AddressDTOOut> addressesDTOOuts = new HashSet<>();

        for (Address address:
                addresses) {
            AddressDTOOut addressDTOOut = new AddressDTOOut();
            modelMapper.map(address, addressDTOOut);
            addressesDTOOuts.add(addressDTOOut);
        }

        logger.info("end: AddressServiceImpl_convertToAddressDTOOutSet");
        return addressesDTOOuts;
    }

    private Address addressInMapping(AddressDTOIn addressDTOIn) {
        logger.info("start: AddressServiceImpl_addressInMapping");
        Address address = new Address();
        modelMapper.map(addressDTOIn, address);
        logger.info("end: AddressServiceImpl_addressInMapping");
        return address;
    }

    private AddressDTOOut addressOutMapping(Address address) {
        logger.info("start: AddressServiceImpl_addressOutMapping");
        AddressDTOOut addressDTOOut = new AddressDTOOut();
        modelMapper.map(address, addressDTOOut);
        logger.info("end: AddressServiceImpl_addressOutMapping");
        return addressDTOOut;
    }

    private Region getRegionEnum(String region) throws RegionNotFoundException {
        logger.info("AddressServiceImpl_getRegionEnum");
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
