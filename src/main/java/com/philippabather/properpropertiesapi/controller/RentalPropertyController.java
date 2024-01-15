package com.philippabather.properpropertiesapi.controller;

import com.philippabather.properpropertiesapi.dto.RentalDTOOut;
import com.philippabather.properpropertiesapi.exception.PropertyNotFoundException;
import com.philippabather.properpropertiesapi.exception.ProprietorNotFoundException;
import com.philippabather.properpropertiesapi.model.RentalProperty;
import com.philippabather.properpropertiesapi.service.RentalPropertyService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(RentalPropertyController.class);
    private final RentalPropertyService rentalService;

    public RentalPropertyController(RentalPropertyService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping("/properties/rental")
    public ResponseEntity<Set<RentalDTOOut>> getAllProperties(@RequestParam(value = "numBedrooms", defaultValue = "0")
                                                                int numBedrooms,
                                                              @RequestParam(value = "minTenancy", defaultValue = "0")
                                                                int minTenancy,
                                                              @RequestParam(value = "monthlyRent", defaultValue = "0.00")
                                                                BigDecimal monthlyRent) {
        logger.info("start: RentalPropertyController_getAllProperties");

        Set<RentalDTOOut> rentalProperties = new HashSet<>();

        if (numBedrooms == 0 && minTenancy == 0 && monthlyRent.toString().equals("0.00")) {
            rentalProperties = rentalService.findAll();
        } else if (numBedrooms > 0) {
            rentalProperties = rentalService.findByNumBedrooms(numBedrooms);
        } else if (minTenancy > 0) {
            rentalProperties = rentalService.findAllByMinTenancy(minTenancy);
        } else if (!monthlyRent.toString().equals("0.00")) {
            rentalProperties = rentalService.findAllByMonthlyRent(monthlyRent);
        }

        logger.info("end: RentalPropertyController_getAllProperties");
        return new ResponseEntity<>(rentalProperties, HttpStatus.OK);
    }


    @PostMapping("/properties/rental/{proprietorId}")
    public ResponseEntity<RentalDTOOut> createRentalProperty(@PathVariable long proprietorId, @Valid @RequestBody RentalProperty rentalProperty)
            throws ProprietorNotFoundException {
        logger.info("start: RentalPropertyController_createRentalProperty");
        RentalDTOOut property = rentalService.save(proprietorId, rentalProperty);
        logger.info("end: RentalPropertyController_createRentalProperty");
        return new ResponseEntity<>(property, HttpStatus.CREATED);
    }

    @GetMapping("/properties/rental/{propertyId}")
    public ResponseEntity<RentalDTOOut> getPropertyById(@PathVariable long propertyId) throws PropertyNotFoundException {
        logger.info("start: RentalPropertyController_getPropertyById");
        RentalDTOOut property = rentalService.findById(propertyId);
        logger.info("end: RentalPropertyController_getPropertyById");
        return new ResponseEntity<>(property, HttpStatus.OK);
    }

    @PutMapping("/properties/rental/{propertyId}")
    public ResponseEntity<RentalDTOOut> updatePropertyById(@PathVariable long propertyId, @Valid @RequestBody RentalProperty rentalProperty)
            throws PropertyNotFoundException {
        logger.info("start: RentalPropertyController_updatePropertyById");
        RentalDTOOut property = rentalService.updateById(propertyId, rentalProperty);
        logger.info("end: RentalPropertyController_updatePropertyById");
        return new ResponseEntity<>(property, HttpStatus.OK);
    }

    @DeleteMapping("/properties/rental/{propertyId}")
    public ResponseEntity<Void> deletePropertyById(@PathVariable long propertyId) throws PropertyNotFoundException {
        logger.info("start: RentalPropertyController_deletePropertyById");
        rentalService.deleteById(propertyId);
        logger.info("end: RentalPropertyController_deletePropertyById");
        return ResponseEntity.noContent().build();
    }
}
