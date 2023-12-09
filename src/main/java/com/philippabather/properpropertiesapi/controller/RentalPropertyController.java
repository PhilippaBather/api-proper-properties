package com.philippabather.properpropertiesapi.controller;

import com.philippabather.properpropertiesapi.exception.PropertyNotFoundException;
import com.philippabather.properpropertiesapi.exception.ProprietorNotFoundException;
import com.philippabather.properpropertiesapi.model.RentalProperty;
import com.philippabather.properpropertiesapi.service.RentalPropertyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * RentalPropertyController - controller para manejar los datos sobre entidaes de un alquiler ('Rental Property').
 *
 * @author Philippa Bather
 */
@Validated
@RestController
public class RentalPropertyController {

    // TODO DTOs
    // TODO validation

    private final RentalPropertyService rentalService;

    public RentalPropertyController(RentalPropertyService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping("/properties/rental")
    public ResponseEntity<Set<RentalProperty>> getAllProperties(@RequestParam(value = "numBedrooms", defaultValue = "0")
                                                                int numBedrooms,
                                                                @RequestParam(value = "minTenancy", defaultValue = "0")
                                                                int minTenancy,
                                                                @RequestParam(value = "monthlyRent", defaultValue = "0.00")
                                                                BigDecimal monthlyRent) {

        Set<RentalProperty> rentalProperties = new HashSet<>();

        if (numBedrooms == 0 && minTenancy == 0 && monthlyRent.toString().equals("0.00")) {
            rentalProperties = rentalService.findAll();
        } else if (numBedrooms > 0) {
            rentalProperties = rentalService.findByNumBedrooms(numBedrooms);
        } else if (minTenancy > 0) {
            rentalProperties = rentalService.findAllByMinTenancy(minTenancy);
        } else if (!monthlyRent.equals("0.00")) {
            rentalProperties = rentalService.findAllByMonthlyRent(monthlyRent);
        }

        return new ResponseEntity<>(rentalProperties, HttpStatus.OK);
    }


    @PostMapping("/properties/rental/{proprietorId}")
    public ResponseEntity<RentalProperty> createProperty(@PathVariable long proprietorId, @Valid @RequestBody RentalProperty rentalProperty)
            throws ProprietorNotFoundException {
        RentalProperty property = rentalService.save(proprietorId, rentalProperty);
        return new ResponseEntity<>(property, HttpStatus.CREATED);
    }

    @GetMapping("/properties/rental/{propertyId}")
    public ResponseEntity<RentalProperty> getPropertyById(@PathVariable long propertyId) throws PropertyNotFoundException {
        RentalProperty property = rentalService.findById(propertyId);
        return new ResponseEntity<>(property, HttpStatus.OK);
    }

    @PutMapping("/properties/rental/{propertyId}")
    public ResponseEntity<RentalProperty> updatePropertyById(@PathVariable long propertyId, @Valid @RequestBody RentalProperty rentalProperty)
            throws PropertyNotFoundException {
        RentalProperty property = rentalService.updateById(propertyId, rentalProperty);
        return new ResponseEntity<>(property, HttpStatus.OK);
    }

    @DeleteMapping("/properties/rental/{propertyId}")
    public ResponseEntity<Void> deletePropertyById(@PathVariable long propertyId) throws PropertyNotFoundException {
        rentalService.deleteById(propertyId);
        return ResponseEntity.noContent().build();
    }
}
