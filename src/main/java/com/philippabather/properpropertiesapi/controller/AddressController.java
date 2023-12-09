package com.philippabather.properpropertiesapi.controller;

import com.philippabather.properpropertiesapi.dto.AddressDTOIn;
import com.philippabather.properpropertiesapi.dto.AddressDTOOut;
import com.philippabather.properpropertiesapi.exception.AddressNotFoundException;
import com.philippabather.properpropertiesapi.exception.PropertyNotFoundException;
import com.philippabather.properpropertiesapi.exception.RegionNotFoundException;
import com.philippabather.properpropertiesapi.service.AddressService;
import com.philippabather.properpropertiesapi.service.RentalPropertyService;
import jakarta.validation.Valid;
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

    // TODO - relationship with Property

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
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @GetMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTOOut> getAddressById(@PathVariable long addressId) throws AddressNotFoundException {
        AddressDTOOut addressDTOOut = addressService.getById(addressId);
        return new ResponseEntity<>(addressDTOOut, HttpStatus.OK);
    }

    @PostMapping("/properties/rental/addresses/{propertyId}")
    public ResponseEntity<AddressDTOOut> createAddress(@PathVariable long propertyId, @Valid @RequestBody AddressDTOIn address) {
        AddressDTOOut addressDTOOut = addressService.saveRentalAddress(propertyId, address);
        return new ResponseEntity<>(addressDTOOut, HttpStatus.CREATED);
    }

    @PutMapping("/properties/rental/addresses/{propertyId}")
    public ResponseEntity<AddressDTOOut> updateAddressById(@PathVariable long propertyId,
                                                           @Valid @RequestBody AddressDTOIn addressDTOIn)
            throws AddressNotFoundException {
        AddressDTOOut addressDTOOut = addressService.updateByPropertyId(propertyId, addressDTOIn);
        return new ResponseEntity<>(addressDTOOut, HttpStatus.OK);
    }

    // NOTE: Delete: también dirigido por la relación el @OneToOne(cascade = CascadeType.ALL)
    @DeleteMapping("/properties/rental/addresses/{propertyId}")
    public ResponseEntity<Void> deleteAddressById(@PathVariable long propertyId) throws PropertyNotFoundException {
        addressService.deleteById(propertyId);
        return ResponseEntity.noContent().build();
    }

}