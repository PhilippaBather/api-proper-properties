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

    @GetMapping("/properties/count/rental")
    public ResponseEntity<Integer> getRentalCountSQLNative() {
        logger.info("start: RentalPropertyController_getRentalCountSQLNative");
        Integer count = rentalService.getRentalCountSQLNative();
        logger.info("end: RentalPropertyController_getRentalCountSQLNative");
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping("/properties/bedrooms/rental/{bedrooms}")
    public ResponseEntity<Set<RentalDTOOut>> getRentalsByBedroomsNativeSQL(@PathVariable int bedrooms){
        logger.info("start: RentalPropertyController_getRentalsByBedroomsNativeSQL");
        Set<RentalDTOOut> rentals = rentalService.getRentalsByBedroomsNativeSQL(bedrooms);
        logger.info("start: RentalPropertyController_getRentalsByBedroomsNativeSQL");
        return new ResponseEntity<>(rentals, HttpStatus.OK);
    }

    @GetMapping("/properties/facilities/rental")
    public ResponseEntity<Set<RentalDTOOut>> getRentalsByParkingAndLiftNativeSQL(){
        logger.info("start: RentalPropertyController_getRentalsByParkingAndLiftNativeSQL");
        Set<RentalDTOOut> rentals = rentalService.getRentalsByParkingAndLiftNativeSQL();
        logger.info("start: RentalPropertyController_getRentalsByParkingAndLiftNativeSQL");
        return new ResponseEntity<>(rentals, HttpStatus.OK);
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
