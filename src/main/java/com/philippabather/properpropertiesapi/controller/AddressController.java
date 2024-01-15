package com.philippabather.properpropertiesapi.controller;

import com.philippabather.properpropertiesapi.dto.AddressDTOIn;
import com.philippabather.properpropertiesapi.dto.AddressDTOOut;
import com.philippabather.properpropertiesapi.exception.AddressNotFoundException;
import com.philippabather.properpropertiesapi.exception.PropertyNotFoundException;
import com.philippabather.properpropertiesapi.exception.RegionNotFoundException;
import com.philippabather.properpropertiesapi.service.AddressService;
import com.philippabather.properpropertiesapi.service.RentalPropertyService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

/**
 * AddressController - controller para manejar los datos sobre entidaes de una dirección ('Address').
 *
 * @author Philippa Bather
 */

@Validated
@RestController
public class AddressController {

    private final Logger logger = LoggerFactory.getLogger(AddressController.class);
    private final AddressService addressService;
    private final RentalPropertyService rentalService;

    public AddressController(AddressService addressService, RentalPropertyService rentalService) {
        this.addressService = addressService;
        this.rentalService = rentalService;
    }

    @GetMapping("/addresses")
    public ResponseEntity<Set<AddressDTOOut>> getAllAddresses(@RequestParam(value = "postCode", defaultValue = "") String postCode,
                                                              @RequestParam(value = "region", defaultValue = "") String region,
                                                              @RequestParam(value = "town", defaultValue = "") String town)
            throws RegionNotFoundException {
        logger.info("start: AddressController_getAllAddresses");
        Set<AddressDTOOut> addresses = new HashSet<>();

        if (postCode.trim().equals("") && region.trim().equals("") && town.trim().equals("")) {
            addresses = addressService.findAll();
        } else if (postCode.trim().length() == 5) {
            addresses = addressService.findAllByPostcode(postCode);
        } else if (region.trim().length() > 1) {
            addresses = addressService.findAllByRegion(region);
        } else if (town.trim().length() > 1) {
            addresses = addressService.findAllByTown(town);
        }
        logger.info("end: AddressController_getAllAddresses");
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @GetMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTOOut> getAddressById(@PathVariable long addressId) throws AddressNotFoundException {
        logger.info("start: AddressController_getAddressesById");
        AddressDTOOut addressDTOOut = addressService.getById(addressId);
        logger.info("end: AddressController_getAddressById");
        return new ResponseEntity<>(addressDTOOut, HttpStatus.OK);
    }

    @PostMapping("/properties/rental/addresses/{propertyId}")
    public ResponseEntity<AddressDTOOut> createRentalAddress(@PathVariable long propertyId, @Valid @RequestBody AddressDTOIn address) {
        logger.info("start: AddressController_createRentalAddress");
        AddressDTOOut addressDTOOut = addressService.saveRentalAddress(propertyId, address);
        logger.info("end: AddressController_createRentalAddress");
        return new ResponseEntity<>(addressDTOOut, HttpStatus.CREATED);
    }

    @PostMapping("/properties/sale/addresses/{propertyId}")
    public ResponseEntity<AddressDTOOut> createSaleAddress(@PathVariable long propertyId, @Valid @RequestBody AddressDTOIn address) {
        logger.info("start: AddressController_createSaleAddress");
        AddressDTOOut addressDTOOut = addressService.saveSaleAddress(propertyId, address);
        logger.info("end: AddressController_createSaleAddress");
        return new ResponseEntity<>(addressDTOOut, HttpStatus.CREATED);
    }

    @PutMapping("/properties/rental/addresses/{propertyId}")
    public ResponseEntity<AddressDTOOut> updateAddressByRentalPropertyId(@PathVariable long propertyId,
                                                           @Valid @RequestBody AddressDTOIn addressDTOIn)
            throws AddressNotFoundException {
        logger.info("start: AddressController_updateAddressByRentalPropertyId");
        AddressDTOOut addressDTOOut = addressService.updateByRentalPropertyId(propertyId, addressDTOIn);
        logger.info("end: AddressController_updateAddressByRentalPropertyId");
        return new ResponseEntity<>(addressDTOOut, HttpStatus.OK);
    }

    @PutMapping("/properties/sale/addresses/{propertyId}")
    public ResponseEntity<AddressDTOOut> updateAddressBySalePropertyId(@PathVariable long propertyId,
                                                           @Valid @RequestBody AddressDTOIn addressDTOIn)
            throws AddressNotFoundException {
        logger.info("start: AddressController_updateAddressBySalePropertyId");
        AddressDTOOut addressDTOOut = addressService.updateBySalePropertyId(propertyId, addressDTOIn);
        logger.info("end: AddressController_updateAddressBySalePropertyId");
        return new ResponseEntity<>(addressDTOOut, HttpStatus.OK);
    }

    // NOTE: Delete: también dirigido por la relación el @OneToOne(cascade = CascadeType.ALL)
    @DeleteMapping("/properties/rental/addresses/{propertyId}")
    public ResponseEntity<Void> deleteAddressById(@PathVariable long propertyId) throws PropertyNotFoundException {
        logger.info("start: AddressController_deleteAddressById");
        addressService.deleteAddressByRentalPropertyId(propertyId);
        logger.info("end: AddressController_deleteAddressById");
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/properties/sale/addresses/{propertyId}")
    public ResponseEntity<Void> deleteAddressBySalePropertyId(@PathVariable long propertyId) throws PropertyNotFoundException {
        logger.info("start: AddressController_deleteAddressBySalePropertyId");
        addressService.deleteAddressBySalePropertyId(propertyId);
        logger.info("end: AddressController_deleteAddressBySalePropertyId");
        return ResponseEntity.noContent().build();
    }

}