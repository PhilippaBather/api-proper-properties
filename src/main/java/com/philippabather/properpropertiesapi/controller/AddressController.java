package com.philippabather.properpropertiesapi.controller;

import com.philippabather.properpropertiesapi.dto.AddressDTOIn;
import com.philippabather.properpropertiesapi.dto.AddressDTOOut;
import com.philippabather.properpropertiesapi.exception.AddressNotFoundException;
import com.philippabather.properpropertiesapi.exception.RegionNotFoundException;
import com.philippabather.properpropertiesapi.service.AddressService;
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

    // TODO - DTOs
    // TODO - relationship with Property
    // TODO - Open Api schema

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/addresses")
    public ResponseEntity<Set<AddressDTOOut>> getAllAddresses(@RequestParam(value = "postCode", defaultValue = "") String postCode,
                                                              @RequestParam(value = "region", defaultValue = "") String region,
                                                              @RequestParam(value = "town", defaultValue = "") String town)
            throws RegionNotFoundException {
        Set<AddressDTOOut> addresses = new HashSet<>();

        if (postCode.trim().equals("") && region.trim().equals("") && town.trim().equals("")) {
            addresses = addressService.getAll();
        } else if (postCode.trim().length() == 5) {
            addresses = addressService.getAllByPostcode(postCode);
        } else if (region.trim().length() > 1) {
            addresses = addressService.getAllByRegion(region);
        } else if (town.trim().length() > 1) {
            addresses = addressService.getAllByTown(town);
        }
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @PostMapping("/addresses")
    public ResponseEntity<AddressDTOOut> createAddress(@Valid @RequestBody AddressDTOIn address) {
        AddressDTOOut addressDTOOut = addressService.save(address);
        return new ResponseEntity<>(addressDTOOut, HttpStatus.CREATED);
    }

    @GetMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTOOut> getAddressById(@PathVariable long addressId) throws AddressNotFoundException {
        AddressDTOOut addressDTOOut = addressService.getById(addressId);
        return new ResponseEntity<>(addressDTOOut, HttpStatus.OK);
    }

    @PutMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTOOut> updateAddressById(@PathVariable long addressId,
                                                           @Valid @RequestBody AddressDTOIn addressDTOIn)
            throws AddressNotFoundException {
        AddressDTOOut addressDTOOut = addressService.updateById(addressId, addressDTOIn);
        return new ResponseEntity<>(addressDTOOut, HttpStatus.OK);
    }

    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<Void> deleteAddressById(@PathVariable long addressId) throws AddressNotFoundException {
        addressService.deleteById(addressId);
        return ResponseEntity.noContent().build();
    }

}